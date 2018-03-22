function clickAutoHide(i,tip,time){
	if(tip==null){
		tip="设置成功！";
	}
	
	if(time==null){
		time=2000;
	}
	switch(i){
		case 1:
			//tip = "服务器繁忙，请稍后再试。";
		break;
		case 4:
			//tip = "设置成功！";
		break;
		case 5:
			//tip = "数据拉取失败";
		break;
		case 6:
			//tip = "正在加载中，请稍后...";
		break;
	}
	ZENG.msgbox.show(tip, i, time);
}