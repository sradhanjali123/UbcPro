package com.hello.controller;
import io.sentry.*;

import java.util.Date;


import java.util.Random;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ubc.pojo.BasicResponseObject;
import com.ubc.pojo.ClickOtp;
import com.ubc.pojo.SocialLoginUsers;


@Path("/clickotp")
public class ClickOtpController {
	public static Logger logger = LogManager.getLogger(ClickOtpController.class);
	public  static  char[] OTP()
    {
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");
 
        // Using numeric values
        String numbers = "0123456789";
        int leng=6;
 
        // Using random method
        Random rndm_method = new Random();
 
        char[] otp = new char[10];
 
        for (int i = 0; i < leng; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }


@POST
@Path("/verifiedotp")
public Response verifyOtp(String request) {
	ClickOtp requestUser = new Gson().fromJson(request, ClickOtp.class);
	try {
		EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
 		if(requestUser.getPhoneno()!=null)
		{
	    eManager.getTransaction().begin();
	    ClickOtp seeuser = new ClickOtp();
	    SocialLoginUsers searchUser = eManager.find(SocialLoginUsers.class, requestUser.getUserid());
		if(searchUser!=null) {
			if(requestUser.getPhoneno()!=null && requestUser.getPhoneno().length()>0) {
				seeuser.setPhoneno(requestUser.getPhoneno());
				seeuser.setTime(new Date());
				seeuser.setOtp(ClickOtpController.OTP());
				seeuser.setEmailId(requestUser.getEmailId());
				seeuser.setUserid(requestUser.getUserid());
				seeuser.setStatus("Verified Email");
			}	
			eManager.persist(seeuser);
			eManager.getTransaction().commit();
			logger.info("otp generated and verified");
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), seeuser).toString())
					.header("Content-Type", "application/json").build();	
		}else {
			logger.error("Response sending Failed");
			return Response.status(Status.NOT_FOUND.getStatusCode())
					.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User phone no Not Found").toString())
					.header("Content-Type", "application/json").build();				
		}
		
		
		}else {
			logger.error("Response sending Failed");
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), "Otp Not Generated").toString())
					.header("Content-Type", "application/json").build();
		}
	} catch (NamingException ex) {
		logger.error("Response sending Failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	} catch (PersistenceException ex) {
		logger.error("Response sending Failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response sending Failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
}
//@POST
//@Path("/verifiedemailotpstatus")
//public Response verifyOtpStatus(String request) {
//	ClickOtp requestUser = new Gson().fromJson(request, ClickOtp.class);
//	try {
//		EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
// 		if(requestUser.getEmailId()!=null)
//	{
//    eManager.getTransaction().begin();
//	ClickOtp seeuser = new ClickOtp();
//	ClickOtp searchUser = eManager.find(ClickOtp.class, requestUser.getUserid());
//	if(searchUser!=null) {
//			if(requestUser.getEmailId()!=null && requestUser.getEmailId().length()>0 && requestUser.getUserid()!=null ) {
//				seeuser.setStatus("Verified Email");
//			
//		} 
//		else { seeuser.setStatus(" Not verified email");}
//		//eManager.persist(seeuser);
//			eManager.getTransaction().commit();
//			return Response.status(Status.OK.getStatusCode())
//				.entity(new BasicResponseObject(Status.OK.getStatusCode(), seeuser).toString())
//				.header("Content-Type", "application/json").build();	
//	}else {
//			return Response.status(Status.NOT_FOUND.getStatusCode())
//					.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User email Not Found").toString())
//					.header("Content-Type", "application/json").build();				
//		}
//		
//		
//		}else {
//			return Response.status(Status.OK.getStatusCode())
//					.entity(new BasicResponseObject(Status.OK.getStatusCode(), "Otp Not Generated").toString())
//					.header("Content-Type", "application/json").build();
//		}
//	} catch (NamingException ex) {
//		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//				.header("Content-Type", "application/json").build();
//	} catch (PersistenceException ex) {
//		ex.printStackTrace();
//		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//			.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//				.header("Content-Type", "application/json").build();
//	} catch (Exception ex) {
//		ex.printStackTrace();
//	return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//				.header("Content-Type", "application/json").build();
//	}
//}

}

