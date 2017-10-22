function setTopic(topicValue) {
//	alert(topicValue);
	$("#topicID").val(topicValue);
}

	
function assignTopic () {
	topicID = $("#topicID").val();
	topicLabelId = $("#topicLabelId").val();
//	alert(topicID + "   topicLabelID: " + topicLabelId);
	if (topicLabelId==0) {
		alert('missing label');
		return;
	}
    $.ajax({
        url: "api/topic",
        type: "POST",
        data: { "topicID" : topicID, "topicLabelId" : topicLabelId },
        success: function(data){
//        	alert('got it!');
        	$('#myModal').modal('toggle');
        	$("#success-alert").fadeTo(2000, 500).slideUp(500, function(){
        	    $("#success-alert").slideUp(500);
        	});
        	$("#topicLabelId").val("0");
        }
    });
}

$(document).ready(function() {
		
	$('#topicTable').hide();	
	$("#success-alert").hide();
	
	// Datepicker
	$('#topicWindowDatePicker').datepicker({
	    startDate: "2012_01",
	    format: "yyyy_mm",
	    startView: 1,
	    minViewMode: 1
	});
	
	$('#topicWindowDatePicker').on('changeDate', function() {
		date = $('#topicWindowDatePicker').datepicker('getFormattedDate')
		
		$.ajax({
	        url: "api/topics",
	        type: "POST",
	        data: { "value": date },
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
                  				  data = '<button type="button" class="btn btn-primary btn-sm"  onclick="setTopic(' + data + ')" data-toggle="modal" data-target="#myModal">' +
                  					  'Assign Label</button>';
                  				
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
              				  data = '<button type="button" class="btn btn-primary btn-sm"  onclick="setTopic(' + data + ')" data-toggle="modal" data-target="#myModal">' +
          					  'Assign Label</button>';
                          }
                          return data;
                      }
                  }
              ]
     	  	})
        }
    });
});



