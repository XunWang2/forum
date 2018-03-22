
/**
 * 详情页面
 * @returns
 */	
var page = 0;	 
var isHave = true;
var childPage = 1;
$(function(){
		  
		  init();
	
	  })
		function init(){  
	if(isHave == true){
		 clickAutoHide(6,"正在加载中，请稍后...",100000);
	}
	
	page++;
	// $("#mark2").html("");
		  var id = GetQueryString("detailId");
		/*  $.post("/forum/detailController/addLook",{"detailId":id,"page":page},function(data){
			  
		  });*/
		  
		$("body").css({"overflow":"hidden"});
		  var userId = $("#userId").html();
		    if(userId != null){
		      $("#denglu").show();
		      $("#comment-list").hide();
		    }else{
		      $("#denglu").hide();
			  $("#comment-list").show();
		    }
		
		  
/* 		  $('img').each(function(){ //jquery.each()循环读取所有图片
		       var height = $(this).height();
		       var width = $(this).width();
		       if(width>380){
		          $(this).css('width','90%');//如果宽度超过200px,高度等比例缩放
		       } */
		       $("#content img").css("cssText","max-width:620px;height:auto !important");
		 // $("#content img").parent().css("cssText","position:absolute;top:0;left:0;width:100%;max-width: 700px;display: flex;justify-content: center;align-items: center;margin: auto");
		
		  /*  var detailId = [[${detailId}]]; 
		   alert(detailId); */
		 var detailId = GetQueryString("detailId");
	 //    $.post("/forum/detailController/detail",{"detailId":detailId},function(data){
   	   $.ajax({
			type:"POST",
			data: "detailId="+detailId+"&page="+page,
			url:"/forum/detailController/detail",
			cache: false,
			async:true,
			success:function(data){
        
	    	 
	    	 if(data.success == "1"){
	    		 $("#title").html(data.one.title);
	    		 $("#content").html(decodeByBase64(data.one.content)); //主体内容
	    		 $("#time").html(data.one.createDate); //时间
	    		 $("#wordLength").html("字数 "+data.one.text.length); //字数
	    		 $("#readTime").html("阅读 "+data.one.lookTime); //阅读次数
	    		 $("#like").html("喜欢 "+data.one.praise);
	    		 $("#likeCount").html(data.one.praise);
	    		 $("#commentComment").html(data.commentComment);
	    		 $("#userInfomation").html("写了 "+data.textCount+" 字，被 695 人关注，获得了 "+data.like+" 个喜欢");
	    		
	    		 var temp = "";
	    		 //评论
	    		
	    		 if( data.CommentList.rows.length== 0){
	    			 clickAutoHide(2,"┗|｀O′|┛ 嗷~~ 没有更多了...",null);
	    			 isHave = false;
	    			 return;
	    		 }
	    		
	    		 data.CommentList.rows.map(function(item,index){
	    			 var num=""; 
	    			 
	    			 var idTrue = false;
	    			 for(var i = 0; i < data.map2.length; i ++){
	    		//		 alert(data.map2[i].commentids+"=="+data.map2[i].userids);
	    				 
	    				 if(item.commentId == data.map2[i].commentids && item.like != 0 && userId == data.map2[i].userids){
	    					 idTrue = true;
	    					 break;
	    				 }
	    				 
	    			 }
	    			 
	    			 if(idTrue){
	    				 num='<span ><img id="zanImg" src="../css/detail/images/1.png" /></span><span id="CommentZan'+item.commentId+'"    class="mr md " style="-moz-user-select:none;" >'+item.like+'</span><span class="mr md" id="ren">人赞</span>';
	    			 }else if(item.like != 0){
	    				 num='<span><img id="zanImg" src="../css/detail/images/zan.png" /></span><span id="CommentZan'+item.commentId+'"    class="mr md " style="-moz-user-select:none;" >'+item.like+'</span><span class="mr md" id="ren">人赞</span>';
	    			 }else{
	    				 num='<span><img id="zanImg" src="../css/detail/images/zan.png" /></span><span id="CommentZan'+item.commentId+'"    class="mr md " style="-moz-user-select:none;" ></span><span class="mr md" id="ren">赞</span>';
	    			 }
	    			 
	    			 var DeleteOrDestroy="举报";
	    			
	    			// alert("评论id="+item.commentId+"  评论用户id="+item.user_id+"=="+userId);
	    	         if(item.user_id == userId ){
	    	        	 DeleteOrDestroy="删除";
	    	         }
	    	         var child ="";
	    	         var rechild = "";
	    	         var addNewComment = '<div class="talk"><a onclick="commentin(this)"  ><img src="../css/detail/images/delete.png">添加新评论</a></div>';	
	    	         
	    	         
	    	     	   $.ajax({
	    					type:"POST",
	    					data: "id="+item.commentId+"&page=1",
	    					url:"/forum/detailController/getChildComment",
	    					cache: false,
	    					async:false,
	    					success:function(data2){
	    	             
	    		
	    	         
	    	         
	    	     //     $.post("/forum/detailController/getChildComment",{"id":item.commentId},function(data2){
	    	        	 if(data2.total > 0){
	    	       
	    	        
	    	        	 data2.rows.map(function(item2,index2){
	    	        		   var DeleteOrDestroy2="举报";
	    	        		   if(item2.uid == userId ){
	    		    	        	 DeleteOrDestroy2="删除";
	    		    	         }
	    	        		 
	    	        		 child = '<div class="people"><span class="cl" data-userSelfId="'+item2.uid+'" >'+item2.username+'</span>：'+
	 	    	        	'<span class="cg myface" id="returnFace2'+index2+'" >'+item2.content+'</span></div>'+
	 	    				'<div  class="date ss"><span>'+item2.create_date+'</span><a class="sm" onclick="commentin2(this)"  ><img src="../css/detail/images/huifu.png">回复</a><span class="delete2 " data-id="'+item2.cid+'"   onclick="deleteChildComment(this)" >'+DeleteOrDestroy2+'</span></div>';
	 	    				
	    	        		 $("#hideface").html(child);
	    	        		 $("#returnFace2"+index2).replaceface($("#returnFace2"+index2).html());
	    	        		 rechild +=  $("#hideface").html();
	    	        	 });
	    	        	  //如果显示的数量小于总数量就拼接字符串
	    	        	 if(data2.rows.length < data2.total){
	    	        		   var ht = "<span id='mores' data-readId='1' data-readmore='"+item.commentId+"' class='cm' onclick='readMoreChildComment(this)' >查看更多</span>";	 
	    	        		   rechild += ht;
	    	        		 }
	    	        	 rechild += addNewComment;
	    	            
	    	        	 
	    	    	
	    	    		 
	    	        	 }
	    					},
		    				
	    				});
	    	     //    });
	    	        
	    	     	 
	    	         temp = '<div class="menu">'+
	    			'<div class="jianjie">'+
	    		'<div class="pic"><img src="../css/detail/images/2-9636b13945b9ccf345bc98d0d81074eb.jpg" /></div>'+
	    		'<div class="title"><span>'+item.username+'</span></div>'+
	    		'<div class="miaoshu"><span>'+(data.CommentList.rows.length - index)+'楼 · '+item.create_Date+' </span></div>'+
	    	'</div>'+
	    	'<div class="content">'+
	    		'<p id="returnFace'+index+'" >'+item.content+'</p>'+
	    	'</div>'+
	    	'<div class="zan">'+
	    		'<a onclick="CommentZan(this)" data-id="'+item.commentId+'" class="fv" style="-moz-user-select:none;" >'+num+'</a>'+
	    		'<span><a onclick="commentin(this)" ><span class="mg"><img src="../css/detail/images/huifu.png" /></span><span class="mr"  >回复</span></a></span>'+
	    		'<span class="delete" id="deleteMyComment" onclick="DeleteByMyComment(this)"  data-delid="'+item.commentId+'" >'+DeleteOrDestroy+'</span>'+
	    		'</div>'+
	    	'<div class="pinglun">'+
	    	rechild +
	    		'<div class="fabiao fabiao2" style="display: none"  >'+
	    			'<textarea onkeydown="keySend(event)" placeholder="写下你的评论..."></textarea>'+
	    			'<div class="bom" data-detailId="'+item.commentId+'" >'+
	    			
	    		  '<div data-v-b36e9416="" class="emoji-modal-wrap">'+
	    	        '<a data-v-b36e9416="" class="emoji">'+
	    	          '<i data-v-b36e9416="" class="iconfont ic-comment-emotions">'+
	    	      	      '<div id="Smohan_FaceBox" name="Smohan_FaceBox2" >'+
	                      '</div>'+
	    	           '</i>'+
	    	         '</a> '+
	    	     ' </div> '+	
	    	      
	    				'<div class="fl"><img id="face" src="../css/detail/images/smile.png" /><span>Ctrl+Enter 发表</span></div>'+
	    				'<div class="fr"><span class="send" onclick="commentout()" >取消</span><span onclick="child_comment(this)"  >发送</span></div>'+
	    			'</div>'+
	    		'</div>'+
	    	'</div>'+
	    '</div>';
	    	         $("#mark2").append(temp);
	    	        $("#returnFace"+index).replaceface($("#returnFace"+index).html());
	    	     //   $(".myface"+index).replaceface($("#returnFace2"+index).html());
	    		 });
	    		
	    		 $("#q_Msgbox").hide();
	    		 initFace();
	    		
	    		 
	    	 }else{
	    		 clickAutoHide(5,data.ErrorMsg,null);
	    	 }
	    	 
	         var commentComment =  $("#commentComment").html();
				if(commentComment == 0){
					$("#noComment").show();
				}
			
	     }
   	   
	     });
	     
	     initLike();
		$(".lading").fadeOut("normal",function(){
			$(this).remove();
		})
		$("body").css({"overflow":"auto"});
	
	  } 

	  
	  function initLike(){
		  var articleId = GetQueryString("detailId");
		    var userId = $("#userId").html();
		    if(userId == null){
		    	return;
		    }

		$.post("/forum/detailController/initLike",{"articleId":articleId},function(data){
		    	 if(data == "yes"){
		    		 $('.note .post .like').addClass('active');
			         $('.note .post .like .fv .tupian').addClass('cur');
		    	 }
		     });
         }
	  
	  //喜欢
	  function like(){
	    var articleId = GetQueryString("detailId");
	    var userId = $("#userId").html();
	    if(userId == null){
	    	location.href="/forum/LoginAndRegMapping/login";
	    	return;
	    }
	    
	    $.post("/forum/detailController/like",{"articleId":articleId},function(data){
	    	if(data == "增加喜欢"){
	    		 var likeCount = $("#likeCount").html();
	    		 likeCount++;
	    		 $("#likeCount").html(likeCount);
	    		 $('.note .post .like').addClass('active');
		         $('.note .post .like .fv .tupian').addClass('cur');
	    	}else if(data == "取消喜欢"){
	    		 var likeCount = $("#likeCount").html();
	    		 likeCount--;
	    		 $("#likeCount").html(likeCount);
	    		 $('.note .post .like').removeClass('active');
                 $('.note .post .like .fv .tupian').removeClass('cur');
	    	}else{
	    		clickAutoHide(5,"发生异常错误",null);
	    	}
	    	});
       }
	  
	  
	  //获取地址栏参数
function GetQueryString(name)
{
var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
var r = window.location.search.substr(1).match(reg);
if(r!=null)return  unescape(r[2]); return null;
}
	  
	  function showOperation(){
		  $("#showOperation").fadeIn();
		  $("#comment").css({"margin-top":"60px"});  
	  }
	  
	  function showOperation2(){
		  $("#showOperation").fadeIn();
		  $("#comment").css({"margin-top":"60px"});  
	  }
	  
	  function cancel(){
		  $("#showOperation").fadeOut();
		  setTimeout(function(){
			  $("#comment").css({"margin-top":"0px"}); 
			  
		  },500);
		  
	  }
	  
	  function initFace(){
		  $("#face").smohanfacebox({

				Event : "click",	//触发事件	

				divid : "Smohan_FaceBox", //外层DIV ID

				textid : "Smohan_text" //文本框 ID

			});
	  }
	  
	  
	  //移除表情
	  function removeFace(){
		  $('.emoji-modal-wrap').hide();
		//	 $btn.removeClass('in');
	  }
	  
	  //ctrl+enter监听
	  function keySend(event) {
		  console.log($(event).parent());
		       
		  if (event.ctrlKey && event.keyCode == 13) {
			 var $btn = event.target;
			 
		//     alert($($btn).val());
		 $($btn).next().children("div:last").children("span:last").click();
		//	 alert($($btn).next().children("div:last").children("span:last").html());
		  }
		  }
	  
	  function showOperation(){
		  $("#showOperation").fadeIn();
		 
	  }
	  
	  

	
	 
/* 		$(function(){
			$('.note .post .like').on('click',function(){
			    if($('.note .post .like').hasClass('active')){
                    $('.note .post .like').removeClass('active');
                    $('.note .post .like .fv .tupian').removeClass('cur');
                  }else{
                  	  $('.note .post .like').addClass('active');
			          $('.note .post .like .fv .tupian').addClass('cur');
                  }
			});       
            
		}); */
		var flag=false;
		//评论框出现
        function commentin(para){
            var userId = $("#userId").html();
    	    if(userId == null){
    	    	location.href="/forum/LoginAndRegMapping/login";
    	    	return;
    	    }
    	    
        var $btn = $(para).parent().parent().parent().children("div:last-child").children("div:last-child");
        var text = $btn.children().eq(0).val();
           if($btn.is(":hidden")){
            	$btn.fadeIn();
            }else{
            	
            if(text != ""){
            	$btn.children().eq(0).val("");
            }else{
            	$btn.fadeOut();
            }
            	
            }
		//	$(para).parent().parent().parent().children("div:last-child").children("div:last-child").fadeIn()
		}
		
		//回复别人评论的评论
        function commentin2(para){
            var userId = $("#userId").html();
    	    if(userId == null){
    	    	location.href="/forum/LoginAndRegMapping/login";
    	    	return;
    	    }
   //     var $btn = $(para).parent().parent().parent().children("div:last-child").children("textarea:first");
        var $btn = $(para).parent().parent().parent().children("div:last-child").children("div:last-child").children("textarea:first");
        var val = $(para).parent().prev().children("span:first").html();
        var textarea = $btn.val();
        
        if($btn.parent().is(":hidden")){
        	$btn.html("@"+val+"&nbsp;");
        	$btn.parent().fadeIn();
        	flag=false;
        }else{
        	if(flag){
        		$btn.parent().fadeOut();
        		flag=false;
        	}else{
        		$btn.val("@"+val+" ");
        		var userSelfId = jQuery($(para).parent().prev().children("span:first")).data('userselfid');
            	//alert(userSelfId);
        		
        	}
        }
        }
		
      //评论框消失
        function commentout(){
        	$('.footer .menu .pinglun .fabiao').fadeOut();
        }
        
        //评论
        function comment(parameter){
            var userId = $("#userId").html();
    	    if(userId == null){
    	    	location.href="/forum/LoginAndRegMapping/login";
    	    	return;
    	    }
        var content = $(parameter).parent().parent().prev().val();
        var articleId = GetQueryString("detailId");
        
        $.post("/forum/detailController/comment",{"content":content,"articleId":articleId},function(data){
        
   
        var temp = '<div class="menu">'+
			'<div class="jianjie">'+
		'<div class="pic"><img src="../css/detail/images/2-9636b13945b9ccf345bc98d0d81074eb.jpg"  /></div>'+
		'<div class="title"><span>'+data.username+'</span></div>'+
		'<div class="miaoshu"><span>'+data.floor+'</span></div>'+
	'</div>'+
	'<div class="content">'+
		'<p id="now" >'+content+'</p>'+
	'</div>'+
	'<div class="zan">'+
//	    				 num='<span><img id="zanImg" src="../css/detail/images/zan.png" /></span><span id="CommentZan'+item.commentId+'"    class="mr md " style="-moz-user-select:none;" ></span><span class="mr md" id="ren">赞</span>';	
		      '<a onclick="CommentZan(this)" data-id="'+data.ct.id+'" ><span><img src="../css/detail/images/zan.png" /></span><span id="CommentZan'+data.ct.id+'" class="mr md"></span><span class="mr md" id="ren">赞</span></a>'+
		'<span><a onclick="commentin(this)"><span class="mg"><img src="../css/detail/images/huifu.png" /></span><span class="mr"  >回复</span></a></span>'+
		'<span class="delete" id="deleteMyComment" onclick="DeleteByMyComment(this)"  data-delid="'+data.ct.id+'" >删除</span>'+
		'</div>'+
	'<div class="pinglun">'+
		'<div class="fabiao fabiao2" style="display: none"  >'+
			'<textarea onkeydown="keySend(event)" placeholder="写下你的评论..."></textarea>'+
			'<div class="bom" data-detailId="'+data.ct.id+'"  >'+
		  '<div data-v-b36e9416="" class="emoji-modal-wrap">'+
	        '<a data-v-b36e9416="" class="emoji">'+
	          '<i data-v-b36e9416="" class="iconfont ic-comment-emotions">'+
	      	      '<div id="Smohan_FaceBox" name="Smohan_FaceBox2" >'+
                  '</div>'+
	           '</i>'+
	         '</a> '+
	     ' </div> '+	
	      
				'<div class="fl"><img id="face" src="../css/detail/images/smile.png" /><span>Ctrl+Enter 发表</span></div>'+
				'<div class="fr"><span class="send" onclick="commentout()" >取消</span><span onclick="child_comment(this)" >发送</span></div>'+
			'</div>'+
		'</div>'+
	'</div>'+
'</div>';
        var num = $("#commentComment").html();
		   num++;
		   $("#commentComment").html(num);
        $("#noComment").hide();
        $("#mark").after(temp);
        $("#now").replaceface($("#now").html());
        clickAutoHide(4,"评论成功",null);
        $(parameter).parent().parent().prev().val("");
        
        });  
        
        }
        
        //评论点赞
        function CommentZan(para) {
     
    //判断用户是否登陆    	
	var userId = $("#userId").html();
	if (userId == null) {
		location.href = "/forum/LoginAndRegMapping/login";
		return;
	}
	//获取评论得Id
	var text = jQuery($(para)).data('id');
	var zana = $("#CommentZan" + text);
	var zan = $("#CommentZan" + text).html();
	var mark;
	//判断用户是否点赞
	$.post("/forum/detailController/judgeIsLike",{"commentId":text},function(data){
		if(data){
			mark = 0;
			
			zan = zan - 1;
			
			if (zan > 0) {
			
				zana.html(zan);
				$(para).children("span:first").children("img").attr("src","../css/detail/images/zan.png");
			} else {
				zana.html("");
				$(para).children("span:first").children("img").attr("src","../css/detail/images/zan.png");
			}
			if (zan < 1) {
				zana.next().html("赞");
				$(para).children("span:first").children("img").attr("src","../css/detail/images/zan.png");
			} else {
				zana.next().html("人赞");

			}
		}else{
			mark = 1
			zan++;
	        		 zana.html(zan);
	        		 zana.next().html("人赞");
	        		 zana.addClass("mark");
	        	$(para).children("span:first").children("img").attr("src","../css/detail/images/1.png");
		}
 	 $.post("/forum/detailController/commentLike",{"mark":mark,"commentId":text},function(data){
  		  
  	 });

		
	});
  } 
        
        /**
         * 删除自己的评论
         */      
          function DeleteByMyComment(para){
       		
       	   var delCommentId = jQuery($(para)).data('delid');
       	   if(delCommentId == "" || delCommentId == undefined){
       		   clickAutoHide(5,"发生了异常",null);
       		   return;
       	   }
       	   var mark  = $(para).html();
       	   if(mark == "删除"){
       		   
       		   var result = event.returnValue = confirm("删除是不可恢复的，你确认要删除吗？");
       		   if(result){
       			   $.post("/forum/detailController/delParentComment",{"id":delCommentId},function(data){
       				   if(data){
       					   var num = $("#commentComment").html();
       					   num--;
       					   $("#commentComment").html(num);
       					   $(para).parent().parent().remove();
       					   if(!$("#mark").next().hasClass("menu")){
       						  $("#noComment").show();
       					   }
       					   clickAutoHide(4,"删除成功",null); 
       				   }else{
       					   clickAutoHide(5,"网络发生异常",null);  
       				   }
       			   });
       		   }
       		   
       	   }else{
       		   //举报
       	   }
          } 
        
        
          function child_comment(para){
      		var content = $(para).parent().parent().prev().val();
      		var articleId = GetQueryString("detailId");
      		var parentCommentId = jQuery($(para).parent().parent()).data('detailid');
      		$.post("/forum/detailController/childComment",{"content":content,"articleId":articleId,"parentCommentId":parentCommentId},function(data){
      			if(data.flag){
      				clickAutoHide(4,data.msg,null);
      				 child = '<div class="people"><span class="cl"  >'+data.username+'</span>：'+
        	        	'<span class="cg myface" id="returnFace2'+data.id+'" >'+content+'</span></div>'+
        				'<div class="date"><span>2018.02.28 12:05</span><a class="sm" onclick="commentin2(this)"  ><img src="../css/detail/images/huifu.png">回复</a><span onclick="deleteChildComment(this)" data-id="'+data.id+'"  class="delete2">删除</span></div>';
        				var $btn = $(para).parent().parent().parent().parent().children("div:first");
        				if($btn.hasClass("fabiao")){
        					 var addNewComment = '<div class="talk"><a onclick="commentin(this)"  ><img src="../css/detail/images/delete.png">添加新评论</a></div>';	
        					$(para).parent().parent().parent().before(child+addNewComment);
        				}else{
        					$(para).parent().parent().parent().parent().children(".date:last").after(child);
        				}
        				
        				$("#returnFace2"+data.id).replaceface($("#returnFace2"+data.id).html());
        				$(".fabiao2").fadeOut();
        				$(para).parent().parent().prev().val("");
      			}else{
      				clickAutoHide(5,data.msg,null);
      			}
      			
      		});
      	}
      	
      	function deleteChildComment(para){
      		var childCommentId = jQuery($(para)).data('id');
      		if($(para).html() == "举报"){
      			return;
      		}
      		  var result = event.returnValue = confirm("删除是不可恢复的，你确认要删除吗？");
      		 if(result == false){
      			 return;
      		 }
      		$.post("/forum/detailController/delChildComment",{"id":childCommentId},function(data){
      			if(data){
      				clickAutoHide(4,"删除成功",null);
      				$(para).parent().prev().remove();
      				$(para).parent().remove();
      				
      			}else{
      				clickAutoHide(5,"删除失败",null);
      			}
      		});
      	}        
        