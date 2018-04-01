$(function() {
	init();
	window.onload = function() {
		$('.lading').hide();
	}

});
var page=0;
function init() {

	 clickAutoHide(6,"正在加载中，请稍后...",2000000);
	 page++;
	 var userId = $("#userIdforhere").html();
	 var userNmae = $("#UserNameforhere").html();
	 var headimgs = $("#UserHeadImgforhere").html();
	 if(userId == ""){
		 clickAutoHide(5,"请登陆",null);
		 return;
	 }
	 
     $.post("/forum/personHomepageController/getData",{"userId":userId,"page":page},function(data){
    	 
    
    	 
    	 if(data.flag){
    		 $("#wzcount").html(data.count);
    		 $("#xhcount").html(data.like);
    		 $("#zscount").html(data.textlength);
    		 
    		 
    		 
    		 
    		 if( data.rows.length >0){
    			 $(".readMore").show();
    		 }
    	
    		 
    		 if(data.flag2 == false){
    			 $(".readMore").hide();
    			  $("#q_Msgbox").hide();
    		 }
    		 data.rows.map(function(item,index){
    			 
    		 var img  = ""; 
    		 var isHaveImg = '<li id="note-11789936" data-note-id="11789936" class="">';
    		 //判断是否有封面
              if(item.cover != null && item.cover != ""){
            	    img  =  '<a class="wrap-img" >'+
	                 '<img class="img-blur-done" src="../upload/'+item.cover+'" alt="120" />'+
                    '</a>';
            	    isHaveImg = '<li id="note-11789936" data-note-id="11789936" class="have-img">';
              }
    		
             var headImg = '<img src="../css/detail/images/2-9636b13945b9ccf345bc98d0d81074eb.jpg" alt="64" />';
                 
             if(headimgs != ""){
            	 headImg = '<img src="../upload'+headimgs+'" alt="64" />';
             }
             
             var count=0;
  		   $.ajax({
				type:"POST",
				data: "cid="+item.id,
				url:"/forum/homeController/getCommentCount",
				cache: false,
				async:false,
				success:function(data){
					
					count = data;
				}
		   
		   });
             
             
    		 var temp = '<ul  id ="del'+item.id+'"  class="note-list" infinite-scroll-url="/u/7434c25bd004?order_by=shared_at">'+
    		                    isHaveImg+img+
				'<div class="content">'+
					'<div class="author">'+
						'<a class="avatar" target="_blank" href="">'+ 
					 	headImg+
						'</a>'+
						'<div class="info">'+
							'<a class="nickname" target="_blank" href="">'+userNmae+'</a> '+
							'<span class="time" data-shared-at="2018-03-06T16:24:50+08:00">'+item.createDate+'</span>'+
						'</div>'+
					'</div>'+
					'<a class="title article" href="/forum/detailMapping/detail2?detailId='+item.id+'">'+item.title+'</a>'+
					'<p class="abstract tep"  >'+item.text+'</p>'+
					'<div class="meta">'+
						'<a target="_blank" href="" > '+
						'<i class="iconfont ic-list-read"  ></i>&emsp;'+item.lookTime+' </a> <a target="_blank" href="">'+
						'<i class="iconfont ic-list-comments"></i>&emsp; '+count+' </a> '+
						'<span><i class="iconfont ic-list-like"></i>&emsp;'+item.praise+'</span>'+
						 '<span class="dedeleteFP2" data-fid="'+item.id+'" onclick="edit(this)" >修改</span> <span class="dedeleteFP" data-fid="'+item.id+'" onclick="del(this)" >删除</span>'
					'</div>'+
				'</div>'+
			'</li>'+
		'</ul>';
    	 
    	    	 $("#list-container").append(temp);
    	         $("#q_Msgbox").hide();
     		 });
    		 
    	 }else{
    	 clickAutoHide(5,"发生异常错误",null);
    	 }
    	 
     });
}


function del(args){
	var id = $(args).data("fid");
    if(id == undefined && id == ""){
    	clickAutoHide(5,"数据异常",null);
        return;
    }
    
    var result = event.returnValue = confirm("删除是不可恢复的，你确认要删除吗？");
    
    if(result){

    $.post("/forum/personHomepageController/deleteOne",{"id":id},function(data){
    	 if(data){
    		 clickAutoHide(4,"删除成功",null);
    		 $("#del"+id).remove();
    	 }else{
    		 clickAutoHide(5,"删除失败",null);
    	 }
    });
    
    }
}

function edit(args){
	
	var id = $(args).data("fid");
    if(id == undefined && id == ""){
    	clickAutoHide(5,"数据异常",null);
        return;
    }
    location.href="/forum/addMapping/edit?editId="+id;
}

// 取消编辑
function cancelEdit() {
	$("#edit_user_5299442").hide();
}

// 编辑个人介绍
function Edit() {
	$("#edit_user_5299442").show();
}





