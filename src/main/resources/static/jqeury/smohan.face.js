/*
 @ 文本框插入表情插件
 @ 作者：水墨寒 Smohan.net
 @ 日期：2013年1月28日
*/
var $b ;
var $btn;
$(function() {

	$.fn.smohanfacebox = function(options) {
	 
		var defaults = {
		Event : "click", //响应事件		
		divid : "Smohan_FaceBox", //表单ID（textarea外层ID）
	/*	textid : "TextArea" //文本框ID
*/		};
		var options = $.extend(defaults,options,this);
		 $btn = $(this);//取得触发事件的ID
		
		//创建表情框
		var faceimg = '';
	    for(i=0;i<60;i++){  //通过循环创建60个表情，可扩展
		 faceimg+='<li><a href="javascript:void(0)"><img src="../face/'+(i+1)+'.gif" face="<emt>'+(i+1)+'</emt>"/></a></li>';
		 };
		 $("div[name=Smohan_FaceBox2]").html("<div id='SmohanFaceBox'  name='SmohanFaceBox' ><span class='Corner'></span><div class='Content2'><ul>"+faceimg+"</ul></div></div>");
	     $('div[name=SmohanFaceBox]').css("display",'block');//创建完成后先将其隐藏
		//创建表情框结束
	    
		var $facepic = $("#SmohanFaceBox li img");
		//BTN触发事件，显示或隐藏表情层
		$btn.live(options.Event,function(e) {
			$b = $(this);
		//	console.log($(this).parent().prev().children("a").children("i").children("div").children("div"));
		//	alert($(this).parent().prev().children("a").children("i").children("div").html())
		//	console.log($btn);
			if($(this).parent().prev().is(":hidden")){
		//		alert();
			  $(this).parent().prev().show();	
			
		//	$(this).parent().prev().children("a").children("i").children("div").children("div").show(360);
		//	$btn.addClass('in');
			}else{
				$(this).parent().prev().hide();
		//	$($(this).parent().prev().children("a").children("i").children("div").children("div")).hide(360);
		//	$btn.removeClass('in');
				}
			});
		//插入表情
		$facepic.die().click(function(){
		     $('.moji-modal-wrap').hide();
			 //$("#"+options.textid).focus();
			 //$("#"+options.textid).val($("#"+options.textid).val()+$(this).attr("face"));f
		   //  alert($("#"+options.textid));
		     console.log($(this));
		     //$(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().parent().prev();
		     $(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().parent().prev().die().insertContent($(this).attr("face"));
		//    var s =  $b.parent().parent().prev().html();
	
		    //	 $btn.removeClass('in');
			});			
		//关闭表情层
		$('#SmohanFaceBox h3 a.close').click(function() {
			
			$('#SmohanFaceBox').hide(360);
			 $btn.removeClass('in');
			});	
		//当鼠标移开时，隐藏表情层，如果不需要，可注释掉
		 $('#SmohanFaceBox').mouseleave(function(){
			// $('#SmohanFaceBox').hide(560);
			// $btn.removeClass('in');
			 });	

  };  
  
  // 【漫画】 光标定位插件
	$.fn.extend({  
		insertContent : function(myValue, t,now) {
		  
			var $t = $(this)[0];  
			console.log(now);
		/*	alert(now.parent().parent().prev().html);*/
			if (document.selection) {  
				this.focus();  
				var sel = document.selection.createRange();  
				sel.text = myValue;  
			
				this.focus();  
				sel.moveStart('character', -l);  
				var wee = sel.text.length;  
				if (arguments.length == 2) {  
				var l = $t.value.length;  
				sel.moveEnd("character", wee + t);  
				t <= 0 ? sel.moveStart("character", wee - 2 * t	- myValue.length) : sel.moveStart("character", wee - t - myValue.length);  
				sel.select();  
				}  
			} else if ($t.selectionStart || $t.selectionStart == '0') {  
				var startPos = $t.selectionStart;  
				var endPos = $t.selectionEnd;  
				var scrollTop = $t.scrollTop;  
				$t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos,$t.value.length);  
				this.focus();  
				$t.selectionStart = startPos + myValue.length;  
				$t.selectionEnd = startPos + myValue.length;  
				$t.scrollTop = scrollTop;  
				if (arguments.length == 2) { 
					$t.setSelectionRange(startPos - t,$t.selectionEnd + t);  
					this.focus(); 
				}  
			} else { 
				alert(3);
				this.value += myValue;                              
				this.focus();  
			}  
		}  
	});
 
 //表情解析
 $.fn.extend({
	  replaceface : function(faces){
		  for(i=0;i<60;i++){
			  faces=faces.replace('<emt>'+ (i+1) +'</emt>','<img src="../face/'+(i+1)+'.gif">');
			  }
		  $(this).html(faces);
		   }
	  });
	  
  
});
