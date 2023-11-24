$(document)
		.ready(
				function() {
					var personId = $("#personId").val();
					$.ajax({
						type : "Get",
						url : "HrPersonServlet",
						cache : false,
						dataType : "json",
						data : {
							"personId" : personId
						},
						success : function(data) {
							//绑定数据
							$("#name").html(data.name);
							$("#gender").html(data.gender);
							$("#birthday").html(data.birthday);
							$("#nation").html(data.nation);
							$("#nativeBirth").html(data.nativeBirth);
							$("#birthAddress").html(data.birthAddress);
							$("#rdTime").html(data.rdTime);
							$("#startWorkTime").html(data.startWorkTime);
							$("#healthy").html(data.healthy);
							$("#speTechPos").html(data.speTechPos);
							$("#specialty").html(data.specialty);
							$("#fulltimeEdu").html(data.fulltimeEdu);
							$("#fulltimeSpe").html(data.fulltimeSpe);
							$("#jobEdu").html(data.jobEdu);
							$("#jobSpe").html(data.jobSpe);
							$("#currPost").html(data.currPost);
							$("#jl").html(data.jl);
							$("#jcqk").html(data.jcqk);
							$("#khqk").html(data.khqk);
							var familyMembers=data.familyMembers;
							var length=familyMembers.length;
							var theadhtml=" <td rowspan="+(length+1)+">主要家庭成员及社会关系</td>"+
							"<td>称谓</td>"+
							"<td>姓名</td>"+
							"<td>出生日期</td>"+
							"<td>政治面貌</td>"+
							"<td>工作单位及职务</td>";
							$("#familyMembersHead").append(theadhtml);
							var rowhtml="";
							for(var i=0;i<length;i++){
								var poltical="";
								if(familyMembers[i].poltical!=null){
									poltical=familyMembers[i].poltical;
								}
								var rowhtml=rowhtml+"<tr class=\"table-list\">"+
									"<td class=\"text-primary\">"+familyMembers[i].cw+"</td>"+
									"<td class=\"text-primary\">"+familyMembers[i].name+"</td>"+
									"<td class=\"text-primary\">"+familyMembers[i].age+"</td>"+
									"<td class=\"text-primary\">"+poltical+"</td>"+
									"<td class=\"text-primary\">"+familyMembers[i].workCompanyPos+"</td>"+
									"</tr>";
							}
							$("#basData").append(rowhtml);
						},

					});

				});