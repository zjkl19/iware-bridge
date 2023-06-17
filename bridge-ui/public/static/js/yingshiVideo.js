$(function () {
	var arry = window.location.search.split('&')
	var videosid = arry[0].split("=")[1];
    var parNam = window.location.search.split("=")[0];
    
    var szIP = null;
	var szPort = null;
	var szUsername = null;
	var szPassword = null;
    // $.ajax({
	// 	type: "POST",
	// 	dataType: "json",
	// 	url: "http://"+window.location.host+"/bridge/Compose/getVideoById",
	// 	data: {
	// 		"id": videosid
	// 	},
	// 	success: function (res) {
	// 		for (let i = 0; i < res.data.length; i++) {
	// 			const element = res.data[i];
	// 			videoConnectionUrl = element.videoConnectionUrl;
	// 			// if( parNam == "?onon"){
	// 			// 	//表示从首页详情跳转过来的
	// 			// 	videoWidth = 300;
	// 			// 	videoHeight = 160;
    //             // }
    //             console.log("~~~~~~~~~~~~~",element)
    //             $('#myPlayer').attr('id','myPlayer_'+videosid);
    //             $('#myPlayer_'+videosid).find("source").attr('src',videoConnectionUrl);
    //             var player = new EZUIPlayer({
	// 				id:'myPlayer_'+videosid,
	// 				autoplay: false,
	// 			});
	// 		}
			 
	// 	},
	// 	error: function (msg) {
	// 		console.log("视频请求失败。请联系管理员");
	// 	}
	// })

	$('#myPlayer').attr('id','myPlayer_'+videosid);
	$('#myPlayer_'+videosid).find("source").attr('src','rtmp://rtmp01open.ys7.com/openlive/830e2633aa924c2eb558f4d2de94a41b.hd');
	var player = new EZUIPlayer({
		id:'myPlayer_'+videosid,
		autoplay: true,
	});
	var id = player.opt.parentId
	// console.log($('#myPlayer_'+videosid).height())
	// $('embed').attr('height','100%');

});