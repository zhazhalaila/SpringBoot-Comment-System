function showComments() {
	$.ajax({
		//Get comment html code
		url:"http://localhost:8080/api/test",
		method:"GET",
		success:function(response) {
			$('#showComments').html(response);
	}
})
}


$(document).ready(function(){
	showComments();
	$('#commentForm').on('submit', function(event){
		event.preventDefault();
		var formData = $(this).serializeArray();
		var formDataObject = {};
		$.each(formData,
			function(i, v) {
				formDataObject[v.name] = v.value;
		});
		console.log(formDataObject);
		$.ajax({
			//Post comment
			url: "http://localhost:8080/api/comments",
			method: "POST",
			data: formDataObject,
			dataType: 'json',
			xhrFields: {
				withCredentials: false
			},

			success:function(response) {
				showComments();
			}
		})
	});
	$(document).on('click', '.reply', function(){
		//Reply to this id
		var commentId = $(this).attr("id");
		$('#parent_id').val(commentId);
		$('#name').focus();

	});
});