var TableEditable = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow,url) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                var butIndex=0;
                for(var rowIndex=0;rowIndex<jqTds.length;rowIndex++){
                	if(jqTds[rowIndex].attributes["editable"]&&jqTds[rowIndex].attributes["editable"].value=="input"){
                		jqTds[rowIndex].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[rowIndex] + '"/>';
                	}
                	if(jqTds[rowIndex].attributes["editable"]&&jqTds[rowIndex].attributes["editable"].value=="button"){
                		if(butIndex==0){
                			jqTds[rowIndex].innerHTML = '<a url='+url+' class="edit" href="">Save</a>';
                		}else{
                			jqTds[rowIndex].innerHTML = '<a class="cancel" href="">Cancel</a>';
                		}
                		butIndex++;
                	}
                }
                oTable.fnDraw();
            }

            function saveRow(oTable, nRow,url) {
            	var jqTds = $('>td', nRow);
            	if(syncSaveValue(jqTds,url)){
            		var butIndex=0;
            		for(var rowIndex=0;rowIndex<jqTds.length;rowIndex++){
            			if(jqTds[rowIndex].attributes["editable"]&&jqTds[rowIndex].attributes["editable"].value=="input"){
            				oTable.fnUpdate($(jqTds[rowIndex].innerHTML).attr("value"), nRow, rowIndex, false);
            			}
            			if(jqTds[rowIndex].attributes["editable"]&&jqTds[rowIndex].attributes["editable"].value=="button"){
            				if(butIndex==0){
            					oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, rowIndex, false);
            				}else{
            					oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, rowIndex, false);
            				}
            				butIndex++;
            			}
            		}
            		oTable.fnDraw();
            		
            	}
                 
            }

            //异步保存
            function syncSaveValue(tdRows,url){
            	var data=createData(tdRows);
            	 $.post(url,data,function(result){
            		 if(result.status!=200){
        				alert("保存失败");
            		 }else{
        				location.reload();
            		 }
            	 });
            }
            function createData(tdRows){
            	var data="";
            	for(var i=0;i<tdRows.length;i++){
            		var $tdHtml=$(tdRows[i]);//定义一个td 的 html
            		if(tdRows[i].attributes["editable"]&&tdRows[i].attributes["editable"].value=="input"){
            			var $inputHtml=$(tdRows[i].innerHTML);//定义一个 td 内的 input 的 html
            			if(data==""){
            				data=data+$tdHtml.attr("name")+"="+$inputHtml.attr("value");
            			}else{
            				data=data+"&"+$tdHtml.attr("name")+"="+$inputHtml.attr("value");
            			}
            			
            		}else if(!tdRows[i].attributes["editable"]){//不可编辑项
            			if(data==""){
            				data=data+$tdHtml.attr("name")+"="+$tdHtml.html();
            			}else{
            				data=data+"&"+$tdHtml.attr("name")+"="+$tdHtml.html();
            			}
            		}
            	}
            	return data;
            }
            var oTable = $('#sample_editable_1').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#sample_editable_1_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('#sample_editable_1_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            jQuery('#sample_editable_1_wrapper .dataTables_length select').select2({
                showSearchInput : false //hide search box with special css class
            }); // initialzie select2 dropdown

            var nEditing = null;

            $('#sample_editable_1_new').click(function (e) {
            	var length=$('#sample_editable_1').find("thead").find("tr").find("th").length;
            	var dataArray=new Array();
            	for(var i=0;i<length-2;i++){
            		dataArray.push('');
            	}
            	dataArray.push('<a url="" class="edit" href="">Edit</a>');
            	dataArray.push('<a class="cancel" data-mode="new" href="">Cancel</a>');
                e.preventDefault();
                // ['', '', '', '','', '', '', '','','<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>']
                var aiNew = oTable.fnAddData(dataArray);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow,"");
                nEditing = nRow;
            });

            $('#sample_editable_1 a.delete').live('click', function (e) {
                e.preventDefault();

                if (confirm("Are you sure to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
                alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            });

            $('#sample_editable_1 a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#sample_editable_1 a.edit').live('click', function (e) {
            	var url=$('#sample_editable_1 a.edit').attr("url");
                e.preventDefault();
                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow,url);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nRow,url);
                    nEditing = null;
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow,url);
                    nEditing = nRow;
                }
            });
        }

    };

}();