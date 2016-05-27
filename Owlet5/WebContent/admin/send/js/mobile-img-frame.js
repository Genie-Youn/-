jQuery(window).load(function(){
	
	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
//	var host = urlObject.attr("host").substring(0,
//			urlObject.attr("host").indexOf(':'));
	var apiName = '/mobile/getImgFrames.do';
	
	var param = location.search;
	
	var host = urlObject.attr("host");
	
	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/' + apiName+param;
	
	jQuery.post(reqURL).done(
			function(data) {
				 var allHtml=data.content;
				 
				 var array=param.split('&');
				 
				 $('.notice-header').attr('href', 'mobile-img-list.html?'+array[0]+'&'+array[1]);
				 
				 if(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>'))){
					 mapJson = JSON.parse(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>')));

					 allHtml = jQuery.parseHTML(allHtml);
					 
				//	 console.log(allHtml);

					 jQuery(allHtml).find('div[id^="doksoft_maps"]');

					 jQuery(allHtml).find('span:not([style^="font-family"])').css('font-family', 'inherit');

					 jQuery('.frame-content').html(allHtml);

					 _loadmap("doksoft_maps2",mapJson);
					 
					 jQuery('#doksoft_maps3').css('width', '100%');
				 }else{
					 jQuery('.frame-content').html(allHtml);
					 
					 jQuery('.pic-slot-box-wrapper').css('width' , '100%');
				 }
	});
});

_loadmap = function(id, json) {
	var canva = $('div[id^="doksoft_maps"]').last()[0];
	
	console.log(canva);
	
	canva.style.width = json.width + "px";
	canva.style.height = json.height + "px";
	var map = new google.maps.Map(canva, {
		zoom : parseInt(json.zoom),
		center : new google.maps.LatLng(parseFloat(json.lat),
				parseFloat(json.lng)),
		mapTypeId : json.type
	});
	if (json.settings) {
		for ( var id in json.settings)
			map.set(id, json.settings[id] ? true : false);
	}
	;
	if (json.objects)
		for ( var type in json.objects) {
			for ( var i in json.objects[type]) {
				var object = 0;
				switch (type) {
				case 'Marker':
					object = new google.maps.Marker({
						position : new google.maps.LatLng(
								json.objects[type][i][0],
								json.objects[type][i][1]),
						map : map,
						title : json.objects[type][i][2]
					});
					(function(txt) {
						google.maps.event.addListener(object, 'click',
								function() {
									(new google.maps.InfoWindow({
										content : txt
									})).open(map, object);
								});
					})(json.objects[type][i][2]);
					break;
				case 'Rectangle':
					object = new google.maps.Rectangle({
						bounds : new google.maps.LatLngBounds(
								new google.maps.LatLng(
										json.objects[type][i][0][0],
										json.objects[type][i][0][1]),
								new google.maps.LatLng(
										json.objects[type][i][1][0],
										json.objects[type][i][1][1])),
						map : map,
					});
					break;
				case 'Polygon':
				case 'Polyline':
					var path = json.objects[type][i], array_path = [];
					for ( var j in path)
						array_path.push(new google.maps.LatLng(path[j][0],
								path[j][1]));
					object = new google.maps[type]({
						path : array_path,
						map : map,
					});
					break;
				case 'Text':
					object = new MarkerWithLabel({
						position : new google.maps.LatLng(
								json.objects[type][i][0],
								json.objects[type][i][1]),
						map : map,
						labelContent : json.objects[type][i][2],
						labelAnchor : new google.maps.Point(22, 0),
						labelClass : "labels",
						labelStyle : {
							opacity : 1.0,
							minWidth : '200px',
							textAlign : 'left'
						},
						icon : {}
					});
					break;
				case 'Circle':
					object = new google.maps.Circle({
						radius : json.objects[type][i][2],
						center : new google.maps.LatLng(
								json.objects[type][i][0],
								json.objects[type][i][1]),
						map : map,
					});
					break;
				case 'WeatherLayer':
					object = new google.maps.weather.WeatherLayer(
							{
								temperatureUnits : google.maps.weather.TemperatureUnit.FAHRENHEIT
							});
					object.setMap(map);
					break;
				case 'TrafficLayer':
					object = new google.maps.TrafficLayer();
					object.setMap(map);
					break;
				}
			}
		}
};
loadmap = function(id, json) {
	google.maps.event.addDomListener(window, 'load', function() {
		_loadmap(id, json)
	});
};