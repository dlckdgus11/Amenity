package com.amenity.business.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amenity.business.service.BusinessService;
import com.amenity.business.vo.BusinessVO;
import com.amenity.company.service.CompanyService;
import com.amenity.company.vo.CompanyVO;

@Controller("BusinessController")
public class BusinessControllerImpl {
	@Autowired(required=true)
	private BusinessService businessService;
	
	@Autowired(required=true)
	BusinessVO businessVO;
	
	@Autowired(required=true)
	private CompanyService companyService;
		
	@Autowired(required=true)
	CompanyVO companyVO;
	
	private static final String COMPANY_IMAGE_REPO="C:\\AM_IMG\\company_image";
	
	@RequestMapping(value = { "/business/b_Info1.do"}, method = RequestMethod.GET)
	private ModelAndView b_Info1(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = { "/business/b_newCompany.do"}, method = RequestMethod.GET)
	private ModelAndView b_newCompany(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	
	
	
	
		//////////////////////////////////////////////////////////////////////////////////////////

		/////                       �����  �α��� 										///////////

		//////////////////////////////////////////////////////////////////////////////////////////


		@RequestMapping(value="/business/b_signIn.do", method=RequestMethod.POST)
		public ModelAndView signIn(@ModelAttribute("businessVO") BusinessVO businessVO, RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception {
				ModelAndView mav = new ModelAndView();
				
				businessVO = businessService.b_signIn(businessVO);
				
					if(businessVO != null) {
						HttpSession session = request.getSession();
						session.setAttribute("businessVO", businessVO);
						session.setAttribute("isLogOn", true);
						String action=(String)session.getAttribute("action");
						session.removeAttribute("action");
						if(action != null) {
							mav.setViewName("redirect:"+action);
						} else {
							mav.setViewName("redirect:/main/main.do");
						}
					} else {
						rAttr.addAttribute("result", "loginFailed");
						mav.setViewName("redirect:/main/b_login.do");
					}
					return mav;
		}
		

		//////////////////////////////////////////////////////////////////////////////////////////

		/////                       �����  ��ü�߰�										///////////

		//////////////////////////////////////////////////////////////////////////////////////////
	
		
		@RequestMapping(value="/business/addNewCompany.do", method=RequestMethod.POST)
		@ResponseBody
		public ResponseEntity addNewCompany(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
				throws Exception {
			multipartRequest.setCharacterEncoding("utf-8");
			Map<String,Object> companyMap = new HashMap<String, Object>();
			Enumeration enu = multipartRequest.getParameterNames();
			while(enu.hasMoreElements()) {
				String name=(String)enu.nextElement();
				String value=multipartRequest.getParameter(name);
				companyMap.put(name, value);	
			}
			String main_img = upload(multipartRequest);
			HttpSession session = multipartRequest.getSession();
			CompanyVO companyVO = (CompanyVO) session.getAttribute("companyVO");
			
			
			
			companyMap.put("main_img", main_img);
			String company=(String)companyMap.get("company");
			String b_no=(String)companyMap.get("b_no");
			System.out.println("b_no : " + b_no);
			String message;
			ResponseEntity resEnt = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			try {
				companyService.addNewCompany(companyMap);
				if(main_img !=null && main_img.length() !=0) {
					File srcFile = new File(COMPANY_IMAGE_REPO+"\\"+"temp"+"\\"+main_img);
					File desDir = new File(COMPANY_IMAGE_REPO+"\\"+b_no +"\\" + company);
					desDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, desDir, true);
				}
				
				message ="<script>";
				message +=" alert('�� ��ü�� �߰��Ͽ����ϴ�.');";
				message +=" location.href='"+multipartRequest.getContextPath()+"/business/b_Info1.do';";
				message +=" </script>";
				resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			} catch(Exception e) {
				File srcFile = new File(COMPANY_IMAGE_REPO+"\\"+"temp"+"\\"+main_img);
				srcFile.delete();
				
				message = " <script>";
				message +=" alert('�߰� �� ������ �߻��Ͽ����ϴ�.');";
				message +=" location.href='"+multipartRequest.getContextPath()+"/business/b_newCompany.do';";
				message +=" </script>";
				resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
				e.printStackTrace();
			}
			return resEnt;
			
		}
		
		
		
		
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////

		/////                     ���� ���ε�												///////////

		//////////////////////////////////////////////////////////////////////////////////////////
		
		private String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
			String main_img =null;
			String sub_img =null;
			Iterator<String> fileNames = multipartRequest.getFileNames();
			
			while(fileNames.hasNext()) {
				String fileName = fileNames.next();
				MultipartFile mFile = multipartRequest.getFile(fileName);
				main_img = mFile.getOriginalFilename();
				File file = new File(COMPANY_IMAGE_REPO+"\\"+"temp"+"\\"+fileName);
				if(mFile.getSize() !=0) {
					if(!file.exists()) {
						file.getParentFile().mkdirs();
						mFile.transferTo(new File(COMPANY_IMAGE_REPO+"\\"+"temp"+"\\"+main_img));
					}
				}
			}
			return main_img;
		}
		
		
		
		
		
		
	
	
	@RequestMapping(value = { "/business/myPage.do"}, method = RequestMethod.GET)
	private ModelAndView myPage(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
}
