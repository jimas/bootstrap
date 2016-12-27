var InitPage=function(){
	return {
		init:function(page){
			page=$.extend(InitPage.getDefaultPage(),page);
			var nextPage=parseInt(page.number)+1>parseInt(page.totalPages)?parseInt(page.totalPages):parseInt(page.number)+1;//下一页
			var prePage=parseInt(page.number)-1<1?1:parseInt(page.number)-1;//上一页
			var pageStart=page.size*(page.number-1)+1;
			var pageEnd=parseInt(page.number*page.size) > parseInt(page.totalElements) ? parseInt(page.totalElements):parseInt(page.number*page.size);
			var $pagePre="<div class='dataTables_info' id='table_data_info'>Showing "+pageStart+" to "+pageEnd+" of "+page.totalElements+" entries</div>";
			
			var $pageNext="<div class='dataTables_paginate paging_bootstrap pagination' style='margin-top: -26px;'> <ul>" ;
			if(page.number==1){
				$pageNext=$pageNext+" <li class='prev disabled'><a>← <span class='hidden-480'>Previous</span></a></li>";
			}else{
				$pageNext=$pageNext+" <li class='prev'><a num="+prePage+" href='javascript:;'>← <span class='hidden-480'>Previous</span></a></li>";
			}
			var $pageTemp="";
			var showPageSize=8;
			
			if(page.totalPages<=showPageSize){//小于9  全部显示
				for(var i=1;i<page.totalPages;i++){
					if(page.number==i){
						$pageTemp=$pageTemp+"<li class='active'><a num="+i+">"+i+"</a></li>";
					}else{
						$pageTemp=$pageTemp+"<li><a num="+i+" href='javascript:;'>"+i+"</a></li>";
					}
				}
			}else{
				if(page.totalPages>showPageSize&&page.number<showPageSize){//总页数大于8，并且显示页码 小于8  
					for(var i=1;i<showPageSize;i++){
						if(page.number==i){
							$pageTemp=$pageTemp+"<li class='active'><a>"+i+"</a></li>";
						}else{
							$pageTemp=$pageTemp+"<li><a num="+i+" href='javascript:;'>"+i+"</a></li>";
						}
					}
					$pageTemp=$pageTemp+"<li><a>...</a></li>" +
					"<li><a href='javascript:;'>"+page.totalPages+"</a></li>";
				}
				else if(page.totalPages>showPageSize&&page.number>=showPageSize&&(page.totalPages-page.number)>showPageSize-3){
					$pageTemp=$pageTemp+"<li><a num='1' href='javascript:;'>"+1+"</a></li>";
					$pageTemp=$pageTemp+"<li><a>...</a></li>";
					for(var j=page.number;j<=page.number+4;j++){
						if(page.number==j){
							$pageTemp=$pageTemp+"<li class='active'><a>"+j+"</a></li>";
						}else{
							$pageTemp=$pageTemp+"<li><a num="+j+" href='javascript:;'>"+j+"</a></li>";
						}
					}
					$pageTemp=$pageTemp+"<li><a>...</a></li>" +
							"<li><a num="+page.totalPages+" href='javascript:;'>"+page.totalPages+"</a></li>";
				}else{
					$pageTemp=$pageTemp+"<li><a num='1' href='javascript:;'>"+1+"</a></li>" +
							"<li><a>...</a></li>";
			
					for(var i=page.totalPages-(showPageSize-2);i<=page.totalPages;i++){
						if(page.number==i){
							$pageTemp=$pageTemp+"<li class='active'><a>"+i+"</a></li>";
						}else{
							$pageTemp=$pageTemp+"<li><a num="+i+" href='javascript:;'>"+i+"</a></li>";
						}
					}
				}
			}
			$pageNext=$pageNext+$pageTemp;
			if(page.number==page.totalPages){
				$pageNext=$pageNext+"<li class='next disabled'><a><span class='hidden-480'>Next</span> → </a></li>" ;
			}else{
				$pageNext=$pageNext+"<li class='next'><a num="+nextPage+" href='javascript:;'><span class='hidden-480'>Next</span> → </a></li>" ;	
			}
			
			var $pageHtml=$($pagePre+$pageNext+"</ul> </div>");
			
			//页码点击事件
			$pageHtml.find("a").click(function(){
				if (typeof(page.callbackfun) == 'function' && $(this).attr("href")){
        			page.callbackfun.call(this,$(this).attr("num"));
        		}
			});
			
			return $pageHtml;
		},
		getDefaultPage : function(){
            return {
    			lang:'cn',//cn:中文；en:英文 （默认中文）
    			number:1,//当前页
    			totalPages:1,//总页数
    			totalElements:1,//总记录数
    			size:10,//默认10行每页
    			isGoPage:false,//false:不展示页码跳转部分；true:展示页码跳转部分 （默认不展示页码跳转部分）			
    			totalRowText:"总记录数",
    			callbackfun:null//分页回调函数
            };
        },
	}
	
	
}();