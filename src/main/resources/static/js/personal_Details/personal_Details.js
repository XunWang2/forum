// JavaScript Document
$(function(){
	$(".lading").fadeOut("normal",function(){
		$(this).remove();
	});
	$('.leftdiv ul li').click(function(){
		$(this).addClass('active');
		$(this).siblings().removeClass("active");
		});
	})
	
	
	$(function(){
 
 	
 	//显示个人信息
 	var user = $("#userId").html();
 	if(user != undefined){
 		var username = $("#UserName").html();
 		$("#nc").val(username);
 		var sex = $("#UserSex").html();
 		if(sex == 1){
 			$("#man").attr('checked', 'true');
 		}else if(sex == 2){
 			$("#woman").attr('checked', 'true');
 		}else if(sex == 0){
 			$("#private").attr('checked', 'true');
 		}
 		
 		var area = $("#UserIntro").html();
 		$("#area").val(area);
 		var userImg = $("#UserHeadImg").html();
 		if(userImg != ""){
 			$("#img").attr("src","../upload/"+userImg);
 		}
 		
 		//$("input[type='radio']:checked").val();
 	}
 	
})

function save(){
	var username = $("#nc").val();
	
	if(username == "" ){
		clickAutoHide(5,"昵称不能为空",null);
		return;
	}
	
	if(username != $("#UserName").html()){
		$.post("/forum/LoginAndRegController/CheckUserName",{"username":username},function(data){
			
			if(data != ""){
				clickAutoHide(5,"昵称已被使用，换一个吧",null);
				return;
			}
			
		});
	}
	
	var sex = $("input[type='radio']:checked").val();
	var area = $("#area").val();
	$.post("/forum/personal_DetailsController/editInformation",{"name":username,"sex":sex,"area":area},function(data){
		
		if(data.flag){
			clickAutoHide(4,data.msg,null);
		}else{
			clickAutoHide(5,data.msg,null);
		}
		
	});
	
	
	//alert(sex);
}



function HeardImgs(){
	var msg = $("#HeardImg").val();
	var junge = msg.lastIndexOf(".");
	var text = msg.substring(junge,msg.length).toLowerCase();
	 if(text != ".jpg" && text != ".png" && text != ".gif" && text != ".jpeg"){
		alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
		$("#HeardImg").val("");
	    return;
	} 
	upImg();
}

function upImg(){
	// 上传设置  
	var  options = {  
	        // 规定把请求发送到那个URL  
	        url: "/forum/personal_DetailsController/uploadImg",  
	        // 请求方式  
	        type: "post",  
	        // 服务器响应的数据类型  
	        dataType: "text",  
	        // 请求成功时执行的回调函数  
	        success: function(data) { 
	        	if(data == "上传失败" || data == "请登陆"){
	        		clickAutoHide(5,data,null);
	        		return;
	        	}else{
	        		clickAutoHide(4,"上传成功",null);
	        	}
	        	
	            // 图片显示地址  
	           $("#img").attr("src","../upload/"+data);
	        }  
	};  

	$("#jvForm").ajaxSubmit(options); 
	
	} 


