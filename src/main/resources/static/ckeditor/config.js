/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	/*config.uiColor = '#ff8a00';*/

	
//	config.removeButtons = 'Form,HiddenField,ImageButton,RemoveFormat,Language,BidiRtl,BidiLtr';
	config.image_previewText=' '; //预览区域显示内容  
    //config.filebrowserImageUploadUrl= "/+rap/ckeditor/upload?type=Images"; //要上传的action或servlet
    config.filebrowserUploadUrl = '/forum/addController/uploadImg2';
  //  config.allowedContent=true;   //关闭标签过滤
    config.fullPage= true;
    
    //设置工具栏可隐藏
    config.toolbarCanCollapse = true; 
    //设置工具栏默认展开
    config.toolbarStartupExpanded = true;
    
    config.toolbarLocation='top';
    config.language ='zh-cn';
    config.baseFloatZIndex = 10000;
  //  config.enterMode = CKEDITOR.ENTER_BR; // 编辑器中回车产生的标签CKEDITOR.ENTER_BR(<br>),CKEDITOR.ENTER_P(<p>),CKEDITOR_ENTER(回车)

/*    CKEDITOR.on( 'instanceReady', function( ev ) { with (ev.editor.dataProcessor.writer) {
    	setRules("p", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("h1", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("h2", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("h3", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("h4", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("h5", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("div", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("table", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("tr", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("td", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("iframe", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("li", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("ul", {indent : false, breakAfterOpen : false, breakBeforeClose : false} );
    	setRules("ol", {indent : false, breakAfterOpen : false, breakBeforeClose : false} ); }
    	});*/
    
   /* CKEDITOR.addCss(".cke_editable{background-color: #1774c8}");  */

};
