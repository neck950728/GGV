function set_containerHeight(){
	var header_height = parseInt($("#header").css("height"));
	var container_height = parseInt($("#container").css("height", "auto").css("height"));
	
	if(container_height < window.innerHeight - header_height){
		$("#container").css("height", window.innerHeight - header_height);
	}
}

$(function(){
	set_containerHeight();
});