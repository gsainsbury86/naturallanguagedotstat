$(document).ready(function() {
	$("#query").keyup(function(event) {
		if (event.keyCode == 13) {
			$("#go").click();
		}
	});

//	var canvas = $('#myCanvas');
//	canvas.width($(window).width());
//	canvas.height($(window).height());
	var numQueriesInCloud = 15;

	$.get('main/randomQueries/'+numQueriesInCloud, function(data){
		$('#query').attr('placeholder',data.queries[0]);

		var list = $("#tags").find('ul');
		for (var i = 0; i < numQueriesInCloud; i++){
			var queryi = data.queries[i];
			list.append('<li><a onclick="'+ "$('#query').val('"+queryi.replace(/(['"])/g, "\\$1")+"'); $('#go').click();"  +'" href="#">'+queryi+'</a></li>');				
		}
		$('#myCanvas').tagcanvas({
			textColour: '#808080',
			outlineColour: '#404040',
			reverse: false,
			depth: 0.33,
			maxSpeed: 0.0001
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
				$("#result").text(data.result.toString().replace(/\B(?=(\d{3})+(?!\d))/g,","));
				$("#url").html('URL : ' + '<a href="'+data.url+'" target="_blank" >' + 'source' + '</a><br>');
				$("#datasetURL").html('<a href="'+data.datasetURL+'" target="_blank" >' + data.datasetName + '</a><br>');
				$('#map').text(' ');
				$.each(	data, function(index) {
					if (index != 'result' && index != 'url' && index != 'datasetName' && index != 'datasetURL') {
						var e = data[index];
						var f = data[0];
						var j;
						/*TODO: Fix for age ranges*/
						if ($.isArray(e) && e.length > 1 && index == "Age") {
							var dataArray = [];
							for (year in e) {
								var word = e[year];
								dataArray.push(word);
							}
							e = dataArray.sort(function(a,b) { return a	- b;});
							if(e.length > 3) {
								e = e[0] + ",... , " + e[e.length - 1];
							} else {
								f=e[0];
								for (j=1; j < e.length; j++){
									f = f + ", " + e[j];
								};
								e=f;
							};
						};
		
						$("#map").append(index + " : " + e + '<br>');
					}

				});
			},
			error: function(jqXHR, textstatus, errorThrown){
				$('#result').text(errorThrown);
			}
		});
	});
});


(function(i, s, o, g, r, a, m) {
	i['GoogleAnalyticsObject'] = r;
	i[r] = i[r] || function() {
		(i[r].q = i[r].q || []).push(arguments);
	}, i[r].l = 1 * new Date();
	a = s.createElement(o), m = s.getElementsByTagName(o)[0];
	a.async = 1;
	a.src = g;
	m.parentNode.insertBefore(a, m);
})(window, document, 'script', '//www.google-analytics.com/analytics.js',
'ga');

ga('create', 'UA-2102677-16', 'auto');
ga('send', 'pageview');