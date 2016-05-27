/**
 * 
 */

function requestUrl(_asynic, _cache, _complete, _data, _dataType, _error, _success, _timeout, _type, _url ){
	$.ajax({
		async		: _async,
		cache		: _cache,
		complete	: _complete,
		data		: _data,
		dataType	: _dataType,
		error		: _error,
		success		: _success,
		timeout		: _timeout,
		type		: _type,
		url			: _url
	});
	//or we can use $.getJSON(url, [, data][, sucess]) method.
}