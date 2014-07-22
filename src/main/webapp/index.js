$(document).ready(function() {
	$("#query").keyup(function(event) {
		if (event.keyCode == 13) {
			$("#go").click();
		}
	});

	$.get('main/randomQueries/8', function(data){
		$('#query').attr('placeholder',data.queries[0]);

		var list = $("#tags").find('ul');
		for (var i = 0; i < 8; i++){
			var queryi = data.queries[i];
			list.append('<li><a onclick="'+ "$('#query').val('"+queryi.replace(/(['"])/g, "\\$1")+"'); $('#go').click();"  +'" href="#">'+queryi+'</a></li>');				
		}
		$('#myCanvas').tagcanvas({
			textColour: '#505050',
			outlineColour: '#a0a0a0',
			reverse: false,
			depth: 0.4,
			maxSpeed: 0.01
		},'tags');
	});


 

	$('#go').click( function() {
		$('#result').text('searching...');
		var url = 'main/query';
		var data = {query:$('#query').val()};
		$.ajax({
			url:url,
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			dataType:"json",
			success: function(data, status, jqXHR) {
				if (status == "error"){
					$('#result').text(xhr.status + " " + xhr.statusText);
				}
				else{
					$("#result").text(data.result.toString().replace(/\B(?=(\d{3})+(?!\d))/g,","));
					$("#url").html('URL : ' + '<a href="'+data.url+'" target="_blank" >' + 'source' + '</a><br>');
					$("#datasetURL").html('<a href="'+data.datasetURL+'" target="_blank" >' + data.datasetName + '</a><br>');
					$('#map').text(' ');
					$.each(	data, function(index) {
						if (index != 'result' && index != 'url' && index != 'datasetName' && index != 'datasetURL') {
							var e = data[index];

							/*TODO: Fix for age ranges*/
							if ($.isArray(e) && e.length > 1 && index == "Age") {
								var dataArray = [];
								for (year in e) {
									var word = e[year];
									dataArray.push(word);
								}
								e = dataArray.sort(function(a,b) { return a	- b;});
								e = e[0] + ", ... , " + e[e.length - 1];
							}
							$("#map").append(index + " : " + e + '<br>');
						}
					});
				}
			}
		});
	});
});