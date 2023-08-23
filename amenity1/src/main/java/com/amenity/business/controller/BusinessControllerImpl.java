package com.amenity.business.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	private static final String COMPANY_IMAGE_REPO="C:\\amenity\\business\\company_image";
	
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
			Map<String, Object> articleMap = new HashMap<String, Object>();
			Enumeration enu = multipartRequest.getParameterNames();
			while(enu.hasMoreElements()) {
				String name = (String)enu.nextElement();
				String value = multipartRequest.getParameter(name);
				articleMap.put(name, value);
			}
			
			List<String> main_imgs = companyMainUpload(multipartRequest);
			List<String> sub_imgs = companySubUpload(multipartRequest);
			String message;
			ResponseEntity resEnt = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
			
			try {
				companyService.addNewCompany(articleMap);
				String company = companyService.companyName(articleMap);
				for(String main_img : main_imgs) {
				    if(main_img != null && main_img.length() != 0) {
				        File srcFile = new File(COMPANY_IMAGE_REPO + "\\" + "temp" + "\\" + main_img);
				        File destDir = new File(COMPANY_IMAGE_REPO + "\\" + company + "\\" + "main_img");
				        FileUtils.moveFileToDirectory(srcFile, destDir, true);
				        Map<String, Object> imageMap = new HashMap<>();
				        imageMap.put("main_img", main_img);
				        imageMap.put("company", company);
				        companyService.insertMainImg(imageMap);
				        System.out.println("main_img name : "+main_img);
				    }
				}
				for(String sub_img : sub_imgs) {
				    if(sub_img != null && sub_img.length() != 0) {
				        File srcFile = new File(COMPANY_IMAGE_REPO + "\\" + "temp" + "\\" + sub_img);
				        File destDir = new File(COMPANY_IMAGE_REPO + "\\" + company + "\\" + "sub_img");
				        FileUtils.moveFileToDirectory(srcFile, destDir, true);
				        Map<String, Object> imageMap = new HashMap<>();
				        imageMap.put("sub_img", sub_img);
				        imageMap.put("company", company);
				        companyService.insertSubImg(imageMap);
				        System.out.println("sub_img name : "+sub_img);
				    }
				}
				message = "<script>";
				message += " alert('성공');";
				message += "location.href='"+multipartRequest.getContextPath()+"/main/main.do';";
				message += " </script>";
				resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			}catch(Exception e) {
				File srcFile = new File(COMPANY_IMAGE_REPO+"\\"+"temp"+"\\"+"delImg");
				srcFile.delete();
				

				message = "<script>";
				message += " alert('실패');";
				message += "location.href='"+multipartRequest.getContextPath()+"/business/b_newCompany';";
				message += " </script>";
				resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
				e.printStackTrace();
			}
			return resEnt;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////

		/////                     ���� ���ε�												///////////

		//////////////////////////////////////////////////////////////////////////////////////////
		
		private List<String> companySubUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
		    List<String> imageFileNames = new ArrayList<>();
		    
		    // �룞�씪�븳 �씠由꾩쓣 媛�吏� 紐⑤뱺 �뙆�씪�쓣 媛��졇�샃�땲�떎.
		    List<MultipartFile> files = multipartRequest.getFiles("sub_img");
		    
		    for (MultipartFile mFile : files) {
		        String originalFileName = mFile.getOriginalFilename();
		        File file = new File(COMPANY_IMAGE_REPO + "\\" + "temp" + "\\" + originalFileName);
		        
		        if (mFile.getSize() != 0) {
		            if (!file.exists()) {
		                file.getParentFile().mkdirs();
		                mFile.transferTo(new File(COMPANY_IMAGE_REPO + "\\" + "temp" + "\\" + originalFileName));
		                System.out.println("upload name : " + originalFileName);
		            }
		        }
		        imageFileNames.add(originalFileName);
		    }
		    
		    return imageFileNames;
		}
		
		

		@RequestMapping(value = { "/business/b_newPwd.do"}, method = RequestMethod.GET)
		private ModelAndView b_newPwd(@RequestParam("b_no") String b_no,HttpServletRequest request, HttpServletResponse response) {
			String viewName = (String)request.getAttribute("viewName");
			System.out.println(viewName);
			ModelAndView mav = new ModelAndView();
			mav.setViewName(viewName);
			mav.addObject("b_no", b_no);
			return mav;
		}

  
		private List<String> companyMainUpload(MultipartHttpServletRequest multipartRequest) throws Exception{
			 List<String> imageFileNames = new ArrayList<>();
			    
			    // �룞�씪�븳 �씠由꾩쓣 媛�吏� 紐⑤뱺 �뙆�씪�쓣 媛��졇�샃�땲�떎.
			    List<MultipartFile> files = multipartRequest.getFiles("main_img");
			    
			    for (MultipartFile mFile : files) {
			        String originalFileName = mFile.getOriginalFilename();
			        File file = new File(COMPANY_IMAGE_REPO + "\\" + "temp" + "\\" + originalFileName);
			        
			        if (mFile.getSize() != 0) {
			            if (!file.exists()) {
			                file.getParentFile().mkdirs();
			                mFile.transferTo(new File(COMPANY_IMAGE_REPO + "\\" + "temp" + "\\" + originalFileName));
			                System.out.println("upload name : " + originalFileName);
			            }
			        }
			        imageFileNames.add(originalFileName);
			    }
			    
			    return imageFileNames;
			}
		
		

		
		
		
	
	
	@RequestMapping(value = { "/business/myPage.do"}, method = RequestMethod.GET)
	private ModelAndView myPage(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/////                     사업자 비밀번호 찾기									///////////
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/business/businessFindPwd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity b_FindPwd(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		
		Map<String, Object> businessMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = multipartRequest.getParameter(name);
			businessMap.put(name, value);
		}
		
		boolean check = businessService.checkBusiness(businessMap); 
		System.out.println("일치여부 : " + check);
		String b_no = (String) businessMap.get("b_no"); 

		System.out.println("b_no : " + b_no);
		
		String message;
		
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		
		if(check) {
			
			message = "<script>";
			message += " alert('회원 정보가 일치합니다. 비밀번호 재설정 화면으로 이동합니다 !');";
			message += "location.href='"+multipartRequest.getContextPath()+"/business/b_newPwd.do?b_no="+b_no+"';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}else {
			message = "<script>";
			message += " alert('회원 정보가 일치하지 않습니다 !');";
			message += "location.href='"+multipartRequest.getContextPath()+"/main/bfind_pwd.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			
		}
		
		return resEnt;
		}
	
	
	
	/////////비밀번호 재설정 /////
	@RequestMapping(value="/business/b_updatePwd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity b_updatePwd(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		
		Map<String, Object> businessMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = multipartRequest.getParameter(name);
			businessMap.put(name, value);
		}
		
		System.out.println("bno: " + businessMap.get("b_no"));
		
		String message;
		businessService.changeB_pwd(businessMap);
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		try {
			
			message = "<script>";
			message += " alert('비밀번호를 성공적으로 변경했습니다.');";
			message += "location.href='"+multipartRequest.getContextPath()+"/main/main.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			message = "<script>";
			message += " alert('비밀변호 재설정에 실패했습니다.');";
			message += "location.href='"+multipartRequest.getContextPath()+"/business/businessFindPwd.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}

		return resEnt;
	}
	




}



















