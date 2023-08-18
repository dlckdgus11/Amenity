package com.amenity.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amenity.goods.service.GoodsService;
import com.amenity.service.MainService;
import com.amenity.user.service.UserService;
import com.amenity.user.vo.UserVO;


@Controller("mainController")
public class MainController {
	@Autowired(required=true)
	private MainService mainService;
	
	@Autowired(required=true)
	private UserService userService;

	@Autowired(required=true)
	private GoodsService goodsService;
	
	@Autowired(required=true)
	private UserService userService;
	
	@Autowired(required=true)

	UserVO userVO;
	
	
	@RequestMapping(value = { "/","/main/main.do"}, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@RequestMapping(value = { "/main/account.do"}, method = RequestMethod.GET)
	private ModelAndView account(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	
	
	@RequestMapping(value = { "/main/b_login.do"}, method = RequestMethod.GET)
	private ModelAndView b_login(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	
	
	@RequestMapping(value = { "/main/u_login.do"}, method = RequestMethod.GET)
	private ModelAndView u_login(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	
	@RequestMapping(value = { "/main/u_signup.do"}, method = RequestMethod.GET)
	private ModelAndView u_signup(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	

	
	@RequestMapping(value="/main/u_addsignup.do", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity u_addsignup(@RequestParam("email") String email, MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		
		while(enu.hasMoreElements()) {
			String u_id = (String)enu.nextElement();
			String value = multipartRequest.getParameter(u_id);
			userMap.put(u_id, value);
		}
		
		String message;
		
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		try {
			userService.u_addsignUp(userMap);
			message = "<script>";
			message += " alert(' �꽦怨듬씈 ');";
			message += "location.href='"+multipartRequest.getContextPath()+"/main/main.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			message = "<script>";
			message += " alert('�릺寃좊깘.');";
			message += "location.href='"+multipartRequest.getContextPath()+"/main/u_signup.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		System.out.println("email : " + email);
		return resEnt;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = { "/main/ufind_id.do"}, method = RequestMethod.GET)
	private ModelAndView ufind_id(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@RequestMapping(value = { "/main/ufind_pwd.do"}, method = RequestMethod.GET)
	private ModelAndView ufind_pwd(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@RequestMapping(value = { "/main/product.do"}, method = RequestMethod.GET)
	private ModelAndView product(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/main/u_addsignup.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity u_addsignup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = multipartRequest.getParameter(name);
			userMap.put(name, value);
		}
		
		String message;
		
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		try {
			userService.u_addsignUp(userMap);
			message = "<script>";

			message += " alert('성공띠.');";

			message += "location.href='"+multipartRequest.getContextPath()+"/main/main.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			message = "<script>";

			message += " alert('실패.');";

			message += "location.href='"+multipartRequest.getContextPath()+"/main/u_signup.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}

		return resEnt;
	}
	
	@PostMapping("/main/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("u_id") String u_id) {		
	    int cnt = mainService.idCheck(u_id);
	    return cnt;
	}

	
//////////////////////////////////////////////////////////////////////////////////////////


/////                        占싸깍옙占쏙옙										///////////

//////////////////////////////////////////////////////////////////////////////////////////


	@RequestMapping(value="/main/u_signIn.do", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("userVO") UserVO userVO, RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		userVO = userService.u_signIn(userVO);
		HttpSession session = request.getSession();
		if(userVO != null && userVO.getAuth() == null) {
			session.setAttribute("userVO", userVO);
			session.setAttribute("isLogOn", true);
			String action=(String)session.getAttribute("action");
			session.removeAttribute("action");
			if(action != null) {
				mav.setViewName("redirect:"+action);
			} else {
				mav.setViewName("redirect:/main/main.do");
			}
		}
		else if(userVO != null && userVO.getAuth() != null) {
			session.setAttribute("userVO", userVO);
			session.setAttribute("auth", userVO.getAuth());
			session.setAttribute("isLogOn", true);
			mav.setViewName("redirect:/main/main.do");
		}
		else {
			rAttr.addAttribute("result", "loginFailed");
			mav.setViewName("redirect:/main/u_login.do.do");
		}
		return mav;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////

/////                        占싸그아울옙										///////////

//////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/main/logout.do", method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("userVO");
		session.removeAttribute("auth");
		System.out.println("占싸그아울옙");
		mav.setViewName("redirect:/main/main.do");
		return mav;
	}
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////

/////                       占쏙옙품占쏙옙占� 占쏙옙占� 									///////////



//////////////////////////////////////////////////////////////////////////////////////////


	@RequestMapping(value="/main/productList.do", method=RequestMethod.GET)
	public ModelAndView listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text; charset=utf-8");
		String viewName = (String)request.getAttribute("viewName");
		List goodsList = goodsService.listGoods();
		//ModelAndView mav = new ModelAndView(viewName);
		ModelAndView mav = new ModelAndView("/main/productList");
		mav.addObject("goodsList", goodsList);
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	private String getViewName(HttpServletRequest rq) throws Exception{
		rq.setCharacterEncoding("utf-8");

		
		String contextPath = rq.getContextPath();
		String uri = (String)rq.getAttribute("javax.servlet.include.request_uri");
		
		if(uri == null || uri.trim().equals("")) {
			 uri = rq.getRequestURI();
		}
		
		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))){
			begin = contextPath.length();
			
		}
		
		int end;
		if(uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if(uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		}else {
			end = uri.length();
		}
		
		String fileName = uri.substring(begin, end);
		if(fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
		}
		if(fileName.indexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());
		}
		System.out.println("controller filename : " + fileName);
		return fileName;
		
		
		
	}
}
