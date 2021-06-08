$(document).ready(function() {
	
	function isemail(email) {
	    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
	};
	
	var actable = $("#accountList").DataTable({
    	"pageLength": 10,
    	"bSort":false,
        "bInfo": false,  
        "bFilter": false,
        "bAutoWidth": true,
        "bLengthChange" : false,
        "processing": true,
        "serverSide": true,
        "ajax" : {
        	url: "getuser",
            type: "GET",
            data: function (d) {
                d.username = $("[data=inusername] input").val();
                d.email    = $("[data=inemail] input").val();
                d.roleId   = $("[data=inrole] option:selected").val();
            }
        },
        "aoColumns": [
            { 
            	"render": $.fn.dataTable.render.text(),
            	"data": "username", 
            	"width": "15%" 
            },
            { 
            	"render": $.fn.dataTable.render.text(),
            	"data": "email", 
            	"width": "20%"  
            },
            { 
            	"data": "roleLabel", 
            	"width": "10%" 
            },
            {
                "mData": "status",
                "mRender": function (data, type, row) {
                	if(data == "ACTIVE"){
                		return "<span class='text-success'>Aktif</span>";
                	} else {
                		return "<span class='text-danger'>Non Aktif</span>";
                	}
                },
            	"width": "10%"
            },
            {
            	"mData": function (source, type, val ) {
            		if(source.status == "ACTIVE"){
            			return source.username;
            		} else {
            			return "";
            		}
            	},
                "mRender": function (data, type, row) {
                	if(data != ""){
                		return "<a href='#' data-toggle='modal' data-user='"+ data +"' data-target='#confirmDelete' data-title='Non Aktifkan Akun' data-message='Apakah Anda yakin ingin Non-Aktifkan akun " + data + " ?'>Non Aktifkan</a>"
                	} else {
                		return "";
                	}
                		
                },
            	"width": "40%"
            }
        ],
        "oLanguage" : {
        	"sZeroRecords" : "<i style='color:grey'>Data tidak ditemukan.</i>"
        }
    });
	
	$('#confirmDelete').on('show.bs.modal', function (e) {
		$("#userwrap").val($(e.relatedTarget).attr('data-user'));
		$message = $(e.relatedTarget).attr('data-message');
		$(this).find('.modal-body p').text($message);
		$title = $(e.relatedTarget).attr('data-title');
		$(this).find('.modal-title').text($title);
	 
		var form = $(e.relatedTarget).closest('form');
		$(this).find('.modal-footer #confirm').data('form', form);
	});
	 
	//<!-- Form confirm (yes/ok) handler, submits form -->
	$('#confirmDelete').find('.modal-footer #confirm').on('click', function(){
		  jQuery.ajax({
			  url: '/account/inactivate?user=' + $("#userwrap").val(),
			  success: function (result) {
				  if(result == "1"){
					  actable.search("").draw();
				  } else {
					  alert("fail");
				  }
			  },
			  async: false
	    });
		$(".close").trigger("click");
		$("#userwrap").val("");
	});
	
    $(".bc").on("click", function(event){
    	actable.search("").draw();
    });
    
    $("[data=inusername] input, [data=inemail] input").on('keypress',function(e) {
        if(e.which == 13) {
        	actable.search("").draw();
        }
    });
    
    $(".bb").on("click", function(event){
    	$("[data=inusername] input").val("");
        $("[data=inemail] input").val("");
        $("[data=inrole] option:first").prop("selected",true);
    	actable.search("").draw();
    });
    /*
    $("[data=inrole] select").on('change', function(event) {
    	if(this.value == "2"){
    		$(".err-company").removeClass("show-err");
    		$("[data=incompany]").removeClass("has-error");
    		$("[data=incompany] select").attr("disabled", true);
        } else {
        	$("[data=incompany] select").attr("disabled", false);
        }
    });
    */
	$("form button.b1").on('click', function(event) {
		
		// Clear Error Flag
		$(".has-error").removeClass("has-error");
		$(".err-username, .err-email, .err-password1, .err-password2, .err-role").removeClass("show-err");
		
        // 0. == Validate Role
        var inrole = $("[data=inrole] option:selected").val();
        if(inrole == ""){
        	$("[data=inrole]").addClass("has-error");
        	$(".err-role").addClass("show-err");
        	$(".err-role .with-errors").text("Role harus di isi.");
        }
        
        // 1. == Validate Username
        var inusername = $("[data=inusername] input").val();
        if(inusername == "" || inusername.length < 3 || inusername.length > 25 || /\s/.test(inusername)){
        	$("[data=inusername]").addClass("has-error");
        	$(".err-username").addClass("show-err");
        	$(".err-username .label-input").css("height","50px");
        	$(".err-username .with-errors").text("Username harus 3-25 karakter dan tidak boleh mengandung Spasi.");
        } else {
	        jQuery.ajax({
	            url: '/account/userya?u=' + inusername,
	            success: function (result) {
	                if(result == "noya"){
	                	$("[data=inusername]").addClass("has-error");
	                	$(".err-username").addClass("show-err");
	                	$(".err-username .with-errors").text("Username sudah digunakan.");
	                }
	            },
	            async: false
	        });
        }
       
        // 2. == Validate Company
        /*
        var incompany = $("[data=incompany] option:selected").val();
        if(inrole == "3" && incompany == ""){
        	$("[data=incompany]").addClass("has-error");
        	$(".err-company").addClass("show-err");
        	$(".err-company .with-errors").text("Perusahaan harus dipilih.");
        }
        */
        
        // 3. == Validate Email
        var inemail = $("[data=inemail] input").val();
        if(inemail == "" || isemail(inemail) == false){
        	$("[data=inemail]").addClass("has-error");
        	$(".err-email").addClass("show-err");
        	$(".err-email .with-errors").text("Email harus di isi dengan format yang benar.");
        } else {
	        jQuery.ajax({
	            url: '/account/emailya?e=' + inemail,
	            success: function (result) {
	            	if(result == "noya"){
	            		$("[data=inemail]").addClass("has-error");
	                	$(".err-email").addClass("show-err");
	                	$(".err-email .with-errors").text("Email sudah digunakan.");
	                }
	            },
	            async: false
	        });
        }

        // 4. == Validate Password
        var pass1 = false;
        var pass2 = false;
        var pasvalidrxp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        var inpassword1 = $("[data=inpassword1] input").val();
        if(inpassword1 == "" || pasvalidrxp.test(inpassword1) == false){
        	$("[data=inpassword1]").addClass("has-error");
        	$(".err-password1").addClass("show-err");
        	$(".err-password1 .label-input").css("height","50px");
        	$(".err-password1 .with-errors").text("Password minimal harus 8 karakter, mengandung Huruf Kecil, Kapital, dan Angka.");
        	$("[data=inpassword2] input").val("");
        } else {
        	pass1 = true;
        }
        var inpassword2 = $("[data=inpassword2] input").val();
        if(pass1 && inpassword2 == ""){
        	$("[data=inpassword2]").addClass("has-error");
        	$(".err-password2").addClass("show-err");
        	$(".err-password2 .with-errors").text("Konfirmasi Password harus di isi.");
        } else {
        	pass2 = true;
        }
        if(pass1 && pass2 && inpassword1 != inpassword2){
        	$("[data=inpassword1]").addClass("has-error");
        	$("[data=inpassword2]").addClass("has-error");
        	$(".err-password2").addClass("show-err");
        	$(".err-password2 .with-errors").text("Konfirmasi Password harus sama.");
        }
        
        if($("form .form-group").hasClass("has-error")){
        	// do nothing
        } else {
        	$("form").submit();
        }
	});
	
	$(".editpass0 button").on('click', function(event) {
		$(".editpass1").show();
		$(".editpass0").hide();
	});
	
	$(".editpass1 .bbatal").on('click', function(event) {
		$(".editpass1").hide();
		$(".editpass0").show();
		$(".has-error").removeClass("has-error");
		$(".err-password0, .err-password1, .err-password2").removeClass("show-err");
	});
	
	$(".bsimpan").on('click', function(){
		var valid = true;
		$(".has-error").removeClass("has-error");
		$(".err-password0, .err-password1, .err-password2").removeClass("show-err");
		
		var inpassword0 = $("[data=inpassword0] input").val();
		if(inpassword0 == ""){
        	$("[data=inpassword0]").addClass("has-error");
        	$(".err-password0").addClass("show-err");
        	$(".err-password0 .with-errors").text("Pasword Lama harus di isi.");
        }
		
		var pass1 = false;
        var pass2 = false;
        var pasvalidrxp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        var inpassword1 = $("[data=inpassword1] input").val();
        if(inpassword1 == "" || pasvalidrxp.test(inpassword1) == false){
        	$("[data=inpassword1]").addClass("has-error");
        	$(".err-password1").addClass("show-err");
        	$(".err-password1 .label-input").css("height","50px");
        	$(".err-password1 .with-errors").text("Password minimal harus 8 karakter, mengandung Huruf Kecil, Kapital, dan Angka.");
        	$("[data=inpassword2] input").val("");
        	valid = false;
        } else {
        	pass1 = true;
        }
        var inpassword2 = $("[data=inpassword2] input").val();
        if(pass1 && inpassword2 == ""){
        	$("[data=inpassword2]").addClass("has-error");
        	$(".err-password2").addClass("show-err");
        	$(".err-password2 .with-errors").text("Konfirmasi Password harus di isi.");
        	valid = false;
        } else {
        	pass2 = true;
        }
        if(pass1 && pass2 && inpassword1 != inpassword2){
        	$("[data=inpassword1]").addClass("has-error");
        	$("[data=inpassword2]").addClass("has-error");
        	$(".err-password2").addClass("show-err");
        	$(".err-password2 .with-errors").text("Konfirmasi Password harus sama.");
        	valid = false;
        }
        
        if(valid == true){
        	$("#formUpdatePass").submit();
        }
	});
	
	$(".bubah").on('click', function(){
		var inprofilimage = $("[data=inprofilimage] input[type=file]").val();
        if(inprofilimage == ""){
        	$("[data=inprofilimage]").addClass("has-error");
        } else {
        	$("#formUpdateImage").submit();
        }
	});
});