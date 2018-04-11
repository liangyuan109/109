

/*$(document).on("click",'#showtext', function() {
	let btn = $(this);
    btn.addClass("loading");
    let databaseName = getSelectedDatabase();
  //  $.post('http://localhost:8080/search/test/' + databaseName, function(data) {
    	let url = '../html/result.html?search=' + databaseName;
    	document.location.replace(url);
    	document.getElementById("txt").value=databaseName;
//    	drawGraph(data);
    	btn.removeClass("loading");
//    }, "json");    
    
});*/

/*$(document).on("click",'#txtclick', function() {
	let btn = $(this);
    btn.addClass("loading");
    let keyword = getSearchKeyword();
    $.post('http://localhost:8080/search/test/' + keyword, function(data) {
    	drawGraph(data);
    	btn.removeClass("loading");
    }, "json");        
});*/

/**
 * get the selected database
 * @returns selected database name
 */
function getSearchKeyword() {
    return $('#txtid').val();
}

function drawGraph(data, initial = true){ 
	$('#resultnum').attr('value',data.num);
	$('#resultinfo').append('<li>About ' + data.num + ' results ' + '(' + data.time + ' ms)</li>');
//	for(let i=0; i<10; i++)
//	{
//		$('#idw0').append('<h1 style="font-size: 48px;">' + data.path[i] + '</h1>');
//	}
//	$('#idurl').append('<a href='+data.url[0]+'>'+'</a>');
	$('#id0').append('<h1 style="font-size: 20px;">' + data.titel[0] + '</h1>');
	$('#id0').append('<h2 style="color:#008000;">' + data.author[0] + '.'+ '('+ data.date[0]+')'+'</h2>');
	$('#id0').append('<a href='+data.url[0]+'>'+data.url[0]+'</a>');
	$('#id0').append('<li style="font-size: 15px;">'+data.content[0]+'</li>');
	
	$('#id1').append('<h1 style="font-size: 20px;">' + data.titel[1] + '</h1>');
	$('#id1').append('<h2 style="color:#008000;">' + data.author[1] + '.'+ '('+ data.date[1]+')'+'</h2>');
	$('#id1').append('<a href='+data.url[1]+'>'+data.url[1]+'</a>');
	$('#id1').append('<li style="font-size: 15px;">'+data.content[1]+'</li>');
	
	$('#id2').append('<h1 style="font-size: 20px;">' + data.titel[2] + '</h1>');
	$('#id2').append('<h2 style="color:#008000;">' + data.author[2] + '.'+'('+data.date[2]+')'+'</h2>');
	$('#id2').append('<a href='+data.url[2]+'>'+data.url[2]+'</a>');
	$('#id2').append('<li style="font-size: 15px;">'+data.content[2]+'</li>');
	
	$('#id3').append('<h1 style="font-size: 20px;">' + data.titel[3] + '</h1>');
	$('#id3').append('<h2 style="color:#008000;">' + data.author[3] + '.'+'('+data.date[3]+')'+'</h2>');
	$('#id3').append('<a href='+data.url[3]+'>'+data.url[3]+'</a>');
	$('#id3').append('<li style="font-size: 15px;">'+data.content[3]+'</li>');
	
	$('#id4').append('<h1 style="font-size: 20px;">' + data.titel[4] + '</h1>');
	$('#id4').append('<h2 style="color:#008000;">' + data.author[4] + '.'+'('+data.date[4]+')'+'</h2>');
	$('#id4').append('<a href='+data.url[4]+'>'+data.url[4]+'</a>');
	$('#id4').append('<li style="font-size: 15px;">'+data.content[4]+'</li>');
	
	$('#id5').append('<h1 style="font-size: 20px;">' + data.titel[5] + '</h1>');
	$('#id5').append('<h2 style="color:#008000;">' + data.author[5] + '.'+'('+data.date[5]+')'+'</h2>');
	$('#id5').append('<a href='+data.url[5]+'>'+data.url[5]+'</a>');
	$('#id5').append('<li style="font-size: 15px;">'+data.content[5]+'</li>');
	}

function linkToText(i){
	alert("hallo");
	var QueryParameter = getParameterByName("searchkey");
//	$.post('http://localhost:8080/search/page/'+ page_id +"/"+ QueryParameter, function(data){
	$.post('http://localhost:8080/search/test/'+ QueryParameter ,function(data){
//	let i = $("#id"+i).text();
	getRelativeUrl(data,i);
	let path = data.path[i];
//	let path = $("#id"+i).text();
	var url = path.substring(50).replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/");
	var relurl = "../"+ url;
//	alert(relurl);
	alert(getRelativeUrl(data,i));
	document.getElementById('id'+i).innerHTML=window.location.href;
	window.location.href = getRelativeUrl(data,i);
//	window.location.href = relurl;
//		},"json");
	},"json");
}
function getRelativeUrl(data,i){
	//let path = data.url[i];
	//var url = path.substring(84).replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/");
	var relurl = data.url[i];
	return relurl;
	
}
//function getUrl(){
//	var url = data.path[1].replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/");
//	return url;
//}
//function getRelativeUrl()
//{
//　　var url = path.replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/").replace(/\\/,"/");
////　　var arr = url.split("/");
//   var start = url.indexOf("/");
//   var relUrl = url.substring(start);
//   return relUrl;
////　return arr[1].substring(arr[1].indexOf("/"));
//}

function emptyGraph(){ 
	
//	for(let i=0; i<10; i++)
	{	
		$('#resultinfo').empty();
		$('#id0').empty();
		$('#id1').empty();
		$('#id2').empty();
		$('#id3').empty();
		$('#id4').empty();
		$('#id5').empty();
		$('#id6').empty();
		$('#id7').empty();
		$('#id8').empty();
		$('#id9').empty();
	}
}
//function getData(data){
////	alert(data.path[0])
//	return data.path[1];
//}	

$( function() {
    var availableTags = [
    	"information retrieval","architecture of a search engine","text transformation","text statistics",
    	"parsing documents","information extraction","link analysis","overview of retrieval models",
    	"empirical models","probabilistic models","probability basics","generative models","algebraic models",
    	"indexing","indexing process","retrieval models", "retrieval tasks",
    	"users and queries", "result presentation","evalution","laboratory experiments","effectiveness metrics",
    	"training and testing","acquisition","crawling the web","conversion","storing documents",
    	"cross-language retrieval","distributed and parallel retrieval","multimedia retrieval",
    	"other application domains","search process","inverted indexes","query processing","index constraction",
    	"empirical models","boolean retrieval","vector space model","okapi bm25","hidden variable models","latent semantic indexing",
    	"explicit semantic analysis"
    ];
    $( "#search-input" ).focus().autocomplete({
      source: availableTags
    });
  } );

$(function () {
	
    var myQueryParameter = getParameterByName("searchkey");
    console.log("Test" + myQueryParameter);
    $("#search-input").val(myQueryParameter);
    
    $.post('http://localhost:8080/search/test/'+myQueryParameter, function(data) {
    	emptyGraph();   	
    	drawGraph(data);
//    	getData(data);
    }, "json");

    function getParameterByName( name ) //courtesy Artem
    {
        name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
        var regexS = "[\\?&]"+name+"=([^&#]*)";
        var regex = new RegExp( regexS );
        var results = regex.exec( window.location.href );
        if( results == null )
            return "";
        else
            return decodeURIComponent(results[1].replace(/\+/g, " "));
    }
});








