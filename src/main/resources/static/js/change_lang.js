
function change_lang(lang){
	$.post("setlang",{"lang":lang},function(result){
		if(result.status!=200){
			alert("切换语音失败");
		}else{
			location.reload();
		}
	  });
} 

