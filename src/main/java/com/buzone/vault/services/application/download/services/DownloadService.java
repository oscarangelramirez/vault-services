package com.buzone.vault.services.application.download.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.buzone.vault.services.application.exceptions.ErrorException;
import com.buzone.vault.services.data.download.AddCfdiDownloadRequest;
import com.buzone.vault.services.data.download.AddDownloadResponse;
import com.buzone.vault.services.data.download.AddPaysheetDownloadRequest;
import com.buzone.vault.services.data.download.GetDownloadResponse;
import com.buzone.vault.services.domain.download.aggregates.Download;
import com.buzone.vault.services.domain.download.aggregates.DownloadRepository;
import com.buzone.vault.services.domain.issue.cfdi.aggregates.IssueCfdi;
import com.buzone.vault.services.domain.issue.cfdi.aggregates.IssueCfdiRepository;
import com.buzone.vault.services.domain.issue.cfdi.aggregates.IssueCfdiSpecification;
import com.buzone.vault.services.domain.issue.paysheet.aggregates.IssuePaySheet;
import com.buzone.vault.services.domain.issue.paysheet.aggregates.IssuePaySheetRepository;
import com.buzone.vault.services.domain.issue.paysheet.aggregates.IssuePaySheetSpecification;
import com.buzone.vault.services.domain.specifications.SearchCriteria;
import com.buzone.vault.services.domain.specifications.SearchOperation;
import com.buzone.vault.services.domain.user.aggregates.User;
import com.buzone.vault.services.domain.user.aggregates.UserRepository;

@Service
public class DownloadService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadService.class);
	
	@Value("${download.folder}")
	private String folder;

	@Autowired
	private DownloadRepository downloadRepository;
	
	@Autowired
	private IssueCfdiRepository issueCfdiRepository;
	
	@Autowired
	private IssuePaySheetRepository issuePaysheetRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("${pdf.xslt.cfdi.32}")
	private String issueCfdiXslt32;
	
	@Value("${pdf.xslt.cfdi.33}")
	private String issueCfdiXslt33;
	
	@Value("${pdf.xslt.paysheet.11}")
	private String issuePaysheetXslt11;
	
	@Value("${pdf.xslt.paysheet.12}")
	private String issuePaysheetXslt12;
	
	@Value("${pdf.folfer.temp}")
	private String pdfFolderTemp;
	
	@Value("${pdf.config.fop}")
	private String pdfConfiguration;

	public List<GetDownloadResponse> findAll(Long idUser) {
		Optional<User> optionalUser = userRepository.findById(idUser);
		User user = optionalUser.get();

		Stream<Download> downloads = user.getDownloads().stream().filter(download -> {
			Date today = new Date();
			long diffTime = download.getEndDate().getTime() - today.getTime();
			long diffDays = diffTime / (1000 * 60 * 60 * 24);
			return diffDays >= 0;
		});

		return downloads
				.sorted(Comparator.comparing(Download::getBeginDate).reversed())
				.map(download -> new GetDownloadResponse(download))
				.collect(Collectors.toList());
	}

	public GetDownloadResponse findById(Long idUser, Long id) {
		Optional<User> optionalUser = userRepository.findById(idUser);
		User user = optionalUser.get();

		Download download = user.getDownloads().stream().filter(d -> d.getId() == id).findAny().orElse(null);

		return new GetDownloadResponse(download);
	}

	public AddDownloadResponse add(Long idUser) throws ErrorException {
		try {
			Optional<User> optionalUser = userRepository.findById(idUser);
			User user = optionalUser.get();

			File userDirectory = new File(folder + File.separator + user.getId().toString());
			if (!userDirectory.exists()) {
				userDirectory.mkdirs();
			}

			DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
			Calendar calendarBeginDate = Calendar.getInstance();
			Calendar calendarEndDate = Calendar.getInstance();
			Date beginDate = new Date();
			Date endDate = new Date();
			calendarBeginDate.setTime(beginDate);
			calendarEndDate.setTime(endDate);
			calendarEndDate.add(Calendar.DATE, 3);

			String zipFileName = df.format(beginDate) + ".zip";

			String zipFilePath = userDirectory.getPath() + File.separator + zipFileName;

			Download download = new Download();
			download.setName(zipFileName);
			download.setBeginDate(calendarBeginDate.getTime());
			download.setEndDate(calendarEndDate.getTime());
			download.setPathFile(zipFilePath);
			download.setStatus(Download.Status.PROCESSING);

			user.addDownload(download);
			download.setUser(user);
			downloadRepository.save(download);
			userRepository.save(user);

			AddDownloadResponse response = new AddDownloadResponse(download);

			return response;
		} catch (Exception exception) {
			throw new ErrorException(0, exception.getMessage());
		}
	}
	
	@Async
	public void generateCfdiDownload(AddDownloadResponse response, AddCfdiDownloadRequest request) {
		try {
			Optional<Download> optionalDownload = downloadRepository.findById(response.getId());
			Download download = optionalDownload.get();
			
			IssueCfdiSpecification issueCfdiSpecification = new IssueCfdiSpecification();
			
			if (!request.getIds().isEmpty()) {
				issueCfdiSpecification.add(new SearchCriteria("id", request.getIds(), SearchOperation.IN));
			}
			if (request.getIdStateSAT().isPresent()) {
				issueCfdiSpecification
						.add(new SearchCriteria("idStateSAT", request.getIdStateSAT().get(), SearchOperation.EQUAL));
			}
			if (request.getVersion().isPresent() &&  !"".equals(request.getVersion().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("version", request.getVersion().get(), SearchOperation.EQUAL));
			}
			if (request.getIssueDateFrom().isPresent()) {
				issueCfdiSpecification
						.add(new SearchCriteria("issueDate", request.getIssueDateFrom().get(), SearchOperation.GREATER_THAN_EQUAL));
			}
			if (request.getIssueDateTo().isPresent()) {
				issueCfdiSpecification
						.add(new SearchCriteria("issueDate", request.getIssueDateTo().get(), SearchOperation.LESS_THAN_EQUAL));
			}
			if (request.getRingingDateFrom().isPresent()) {
				issueCfdiSpecification
						.add(new SearchCriteria("ringingDate", request.getRingingDateFrom().get(), SearchOperation.GREATER_THAN_EQUAL));
			}
			if (request.getRingingDateTo().isPresent()) {
				issueCfdiSpecification
						.add(new SearchCriteria("ringingDate", request.getRingingDateTo().get(), SearchOperation.LESS_THAN_EQUAL));
			}
			if (request.getUuid().isPresent() &&  !"".equals(request.getUuid().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("uuid", UUID.fromString(request.getUuid().get()), SearchOperation.EQUAL));
			}
			if (request.getIssueRfc().isPresent() &&  !"".equals(request.getIssueRfc().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("issueRfc", request.getIssueRfc().get(), SearchOperation.EQUAL));
			}
			if (request.getIssue().isPresent() &&  !"".equals(request.getIssue().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("issue", request.getIssue().get(), SearchOperation.EQUAL));
			}
			if (request.getExpeditionPlace().isPresent() &&  !"".equals(request.getExpeditionPlace().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("expeditionPlace", request.getExpeditionPlace().get(), SearchOperation.EQUAL));
			}
			if (request.getReceiverRfc().isPresent() &&  !"".equals(request.getReceiverRfc().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("receiverRfc", request.getReceiverRfc().get(), SearchOperation.EQUAL));
			}
			if (request.getReceiver().isPresent() &&  !"".equals(request.getReceiver().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("receiver", request.getReceiver().get(), SearchOperation.EQUAL));
			}
			if (request.getUse().isPresent() && !"".equals(request.getUse().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("use", request.getUse().get(), SearchOperation.EQUAL));
			}
			if (request.getTotal().isPresent()) {
				issueCfdiSpecification
						.add(new SearchCriteria("total", request.getTotal().get(), SearchOperation.EQUAL));
			}
			if (request.getCurrency().isPresent() &&  !"".equals(request.getCurrency().get())) {
				issueCfdiSpecification
						.add(new SearchCriteria("currency", request.getCurrency().get(), SearchOperation.EQUAL));
			}
			
			List<IssueCfdi> issueCfdis = issueCfdiRepository.findAll(issueCfdiSpecification);
			
			generateCfdiZip(download.getPathFile(), issueCfdis);
	        
	        download.setStatus(Download.Status.GENERATED);
	        downloadRepository.save(download);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage(), exception);
		}
	}
	
	@Async
	public void generatePaysheetDownload(AddDownloadResponse response, AddPaysheetDownloadRequest request) {
		try {
			Optional<Download> optionalDownload = downloadRepository.findById(response.getId());
			Download download = optionalDownload.get();
			
			IssuePaySheetSpecification issuePaysheetSpecification = new IssuePaySheetSpecification();

			if (!request.getIds().isEmpty()) {
				issuePaysheetSpecification.add(new SearchCriteria("id", request.getIds(), SearchOperation.IN));
			}
			if (request.getIdStateSAT().isPresent()) {
				issuePaysheetSpecification
						.add(new SearchCriteria("idStateSAT", request.getIdStateSAT().get(), SearchOperation.EQUAL));
			}
			if (request.getIssueDateFrom().isPresent()) {
				issuePaysheetSpecification.add(new SearchCriteria("issueDate", request.getIssueDateFrom().get(),
						SearchOperation.GREATER_THAN_EQUAL));
			}
			if (request.getIssueDateTo().isPresent()) {
				issuePaysheetSpecification.add(
						new SearchCriteria("issueDate", request.getIssueDateTo().get(), SearchOperation.LESS_THAN_EQUAL));
			}
			if (request.getUuid().isPresent() && !"".equals(request.getUuid().get())) {
				issuePaysheetSpecification
						.add(new SearchCriteria("uuid", UUID.fromString(request.getUuid().get()), SearchOperation.EQUAL));
			}
			if (request.getReceiverRfc().isPresent() && !"".equals(request.getReceiverRfc().get())) {
				issuePaysheetSpecification
						.add(new SearchCriteria("receiverRfc", request.getReceiverRfc().get(), SearchOperation.EQUAL));
			}
			if (request.getReceiver().isPresent() && !"".equals(request.getReceiver().get())) {
				issuePaysheetSpecification
						.add(new SearchCriteria("receiver", request.getReceiver().get(), SearchOperation.EQUAL));
			}
			if (request.getEmployerRegister().isPresent() && !"".equals(request.getEmployerRegister().get())) {
				issuePaysheetSpecification.add(
						new SearchCriteria("employerRegister", request.getEmployerRegister().get(), SearchOperation.EQUAL));
			}
			if (request.getType().isPresent() && !"".equals(request.getType().get())) {
				issuePaysheetSpecification.add(new SearchCriteria("type", request.getType().get(), SearchOperation.EQUAL));
			}
			if (request.getPaymentDateFrom().isPresent()) {
				issuePaysheetSpecification.add(new SearchCriteria("paymentDate", request.getPaymentDateFrom().get(),
						SearchOperation.GREATER_THAN_EQUAL));
			}
			if (request.getPaymentDateTo().isPresent()) {
				issuePaysheetSpecification.add(new SearchCriteria("paymentDate", request.getPaymentDateTo().get(),
						SearchOperation.LESS_THAN_EQUAL));
			}
			if (request.getTotal().isPresent()) {
				issuePaysheetSpecification
						.add(new SearchCriteria("total", request.getTotal().get(), SearchOperation.EQUAL));
			}
			if (request.getVersion().isPresent() && !"".equals(request.getVersion().get())) {
				issuePaysheetSpecification
						.add(new SearchCriteria("version", request.getVersion().get(), SearchOperation.EQUAL));
			}
			if (request.getCurrency().isPresent() && !"".equals(request.getCurrency().get())) {
				issuePaysheetSpecification
						.add(new SearchCriteria("currency", request.getCurrency().get(), SearchOperation.EQUAL));
			}
			if (request.getReceiverCurp().isPresent() && !"".equals(request.getReceiverCurp().get())) {
				issuePaysheetSpecification
						.add(new SearchCriteria("receiverCurp", request.getReceiverCurp().get(), SearchOperation.EQUAL));
			}
			if (request.getSocialSecurityNumber().isPresent() && !"".equals(request.getSocialSecurityNumber().get())) {
				issuePaysheetSpecification.add(new SearchCriteria("socialSecurityNumber",
						request.getSocialSecurityNumber().get(), SearchOperation.EQUAL));
			}
			if (request.getEmployeeNumber().isPresent() && !"".equals(request.getEmployeeNumber().get())) {
				String[] employeeNumbers = request.getEmployeeNumber().get().split("\\|");
				List<String> list = new ArrayList<String>();
				for (String employeeNumber : employeeNumbers) {
					list.add(employeeNumber.trim());
				}
				issuePaysheetSpecification.add(new SearchCriteria("employeeNumber", list, SearchOperation.IN));
			}
			if (request.getDepartment().isPresent() && !"".equals(request.getDepartment().get())) {
				issuePaysheetSpecification
						.add(new SearchCriteria("department", request.getDepartment().get(), SearchOperation.EQUAL));
			}
			if (request.getNumberDaysPaid().isPresent()) {
				issuePaysheetSpecification.add(
						new SearchCriteria("numberDaysPaid", request.getNumberDaysPaid().get(), SearchOperation.EQUAL));
			}
			
			List<IssuePaySheet> issuePaysheets = issuePaysheetRepository.findAll(issuePaysheetSpecification);
			
			generatePaysheetZip(download.getPathFile(), issuePaysheets);
	        
	        download.setStatus(Download.Status.GENERATED);
	        downloadRepository.save(download);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage(), exception);
		}
	}
	
	private void generateCfdiZip(String pathFile, List<IssueCfdi> issueCfdis) throws IOException {
		FileOutputStream fos = new FileOutputStream(pathFile);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		
		byte[] bytes = new byte[1024];
        int length;
        
		for (IssueCfdi issueCfdi : issueCfdis) {
			try {
				String xsltPath = "";
				String xmlPath = issueCfdi.getFilePath();
				
				if("3.2".equals(issueCfdi.getVersion())) {
					xsltPath = issueCfdiXslt32;
				}
				else if("3.3".equals(issueCfdi.getVersion())) {
					xsltPath = issueCfdiXslt33;
				}
				
				File fileXml = new File(xmlPath);
	            FileInputStream fileInputStreamXml = new FileInputStream(fileXml);
	            ZipEntry zipEntryXml = new ZipEntry(fileXml.getName());
	            zipOut.putNextEntry(zipEntryXml);
	            
	            bytes = new byte[1024];
	            while((length = fileInputStreamXml.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            fileInputStreamXml.close();
	            
	            String fileName = fileXml.getName().substring(0, fileXml.getName().lastIndexOf(".")) + ".pdf";
	            String filePathPdf = pdfFolderTemp + File.separator + fileName;
	            
	            File filePdf = generatePdf(xsltPath, xmlPath, filePathPdf);
	            FileInputStream fileInputStreamPdf = new FileInputStream(filePdf);
	            ZipEntry zipEntryPdf = new ZipEntry(filePdf.getName());
	            zipOut.putNextEntry(zipEntryPdf);
	            
	            bytes = new byte[1024];
	            while((length = fileInputStreamPdf.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            fileInputStreamPdf.close();
	            filePdf.delete();
			}
			catch(Exception exception) {
				LOGGER.error(exception.getMessage(), exception);
				continue;
			}
		}
		
		zipOut.close();
        fos.close();
	}
	
	private void generatePaysheetZip(String pathFile, List<IssuePaySheet> issuePaysheets) throws IOException {
		FileOutputStream fos = new FileOutputStream(pathFile);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		
		byte[] bytes = new byte[1024];
        int length;
        
		for (IssuePaySheet issuePaysheet : issuePaysheets) {
			try {
				String xsltPath = "";
				String xmlPath = issuePaysheet.getFilePath();
				
				if("1.1".equals(issuePaysheet.getVersion())) {
					xsltPath = issuePaysheetXslt11;
				}
				else if("1.2".equals(issuePaysheet.getVersion())) {
					xsltPath = issuePaysheetXslt12;
				}
				
				File fileXml = new File(xmlPath);
	            FileInputStream fileInputStreamXml = new FileInputStream(fileXml);
	            ZipEntry zipEntryXml = new ZipEntry(fileXml.getName());
	            zipOut.putNextEntry(zipEntryXml);
	            
	            bytes = new byte[1024];
	            while((length = fileInputStreamXml.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            fileInputStreamXml.close();
	            
	            String fileName = fileXml.getName().substring(0, fileXml.getName().lastIndexOf(".")) + ".pdf";
	            String filePathPdf = pdfFolderTemp + File.separator + fileName;
	            
	            File filePdf = generatePdf(xsltPath, xmlPath, filePathPdf);
	            FileInputStream fileInputStreamPdf = new FileInputStream(filePdf);
	            ZipEntry zipEntryPdf = new ZipEntry(filePdf.getName());
	            zipOut.putNextEntry(zipEntryPdf);
	            
	            bytes = new byte[1024];
	            while((length = fileInputStreamPdf.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            fileInputStreamPdf.close();
	            filePdf.delete();
			}
			catch(Exception exception) {
				LOGGER.error(exception.getMessage(), exception);
				continue;
			}
		}
		
		zipOut.close();
        fos.close();
	}
	
	private File generatePdf(String xsltPath, String xmlPath, String filePath) throws IOException, FOPException, TransformerException, SAXException {		
        File xsltFile = new File(xsltPath);
        StreamSource xmlSource = new StreamSource(new File(xmlPath));

		FopFactory factory = FopFactory.newInstance(new File(pdfConfiguration));
		FOUserAgent foUserAgent = factory.newFOUserAgent();
		
		FileOutputStream out = new FileOutputStream(filePath);
		Fop fop = factory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltFile));
		Result res = new SAXResult(fop.getDefaultHandler());
		transformer.transform(xmlSource, res);
		out.close();
		
		File temp = new File(filePath);
		
		return temp;
	}
}