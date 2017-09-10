$(document).ready(function() {
	
/*	$.get( "api/topics", function( data ) {
	  //	  alert( "Data Loaded: " + data );
	  $('#topicTable').DataTable( {
		  data: data
	  });
	});*/
	
	$('#topicTable').hide();

	
	
	$.ajax({
	    url:'api/windows',
	    type:'GET',
	    dataType: 'json',
	    success: function( json ) {
	        $.each(json, function(i, value) {
	            $('#topicWindowsSelect').append($('<option>').text(value).attr('data-tokens', value));
	        });
	    }
	});
	
	
	$('#topicWindowsSelect').change(function(){
	    $.ajax({
	        url: "api/topics",
	        type: "POST",
	        data: { "value": $("#topicWindowsSelect").val() },
	        dataType:"json",
	        type: "post",
	        success: function(data){
	        	$('#topicTable').show();
	        	$("#topicTable").dataTable().fnDestroy();
	     	  	$('#topicTable').DataTable({
	    		  data: data,
                  paging: false,
                  columnDefs: [
                      {
                          targets:1,
                          render: function ( data, type, row, meta ) {
                              if(type === 'display'){
                                  data = '<a href="' + data + '">Add Label</a>';
                              }
                              return data;
                          }
                      }
                  ]
	     	  	})
	        }
	    });
	});

        
} );

//With JQuery
$("#dynamicRangeSlider").slider();
$("#dynamicRangeSlider").on("slide", function(slideEvt) {
	$("#ex6SliderVal").text(slideEvt.value);
    $.ajax({
        url: "api/dynamictopics",
        type: "POST",
        data: { "value": slideEvt.value },
        dataType:"json",
        type: "post",
        success: function(data){
        	$('#topicTable').show();
        	$("#topicTable").dataTable().fnDestroy();
     	  	$('#topicTable').DataTable( {
    		  data: data,
              paging: false,columnDefs: [
                  {
                      targets:1,
                      render: function ( data, type, row, meta ) {
                          if(type === 'display'){
                              data = '<a href="' + data + '">Add Label</a>';
                          }
                          return data;
                      }
                  }
              ]
     	  	})
        }
    });
});

