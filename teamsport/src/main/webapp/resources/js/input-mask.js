//== Class definition

var BootstrapTimepicker = function () {
    
    //== Private functions
    var demos = function () {
        // minimum setup
        $('.m-timepicker').timepicker({
        	minuteStep: 1,
        	showSeconds: true,
        	showMeridian:false,
        });
    }
    return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();

//== Class definition

var Autosize = function () {
    
    //== Private functions
    var demos = function () {
        // basic demo
        var demo = $('#txtDescription');
        autosize(demo);
    }

    return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();

//== Class definition

var RemoveInputmask = function () {

    //== Private functions
	var demos = function () {
		$(".phone_mask").each(function (index) {
	        if (this.inputmask) {
	            this.inputmask.remove();
	        }
	    });
		$(".number_mask").each(function (index) {
	        if (this.inputmask) {
	            this.inputmask.remove();
	        }
	    });
		$(".integer_mask").each(function (index) {
	        if (this.inputmask) {
	            this.inputmask.remove();
	        }
	    });
		$(".money_mask").each(function (index) {
	        if (this.inputmask) {
	            this.inputmask.remove();
	        }
	    });
	}
	return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();

//== Class definition

var Inputmask = function () {
    
    //== Private functions
    var demos = function () {
        // date format
        $(".date_mask").inputmask("dd/mm/yyyy", {
            autoUnmask: true
        });

        // phone number format
        $(".phone_mask").inputmask("mask", {
            mask: "(999) 999-9999"
        }); 

        // repeating mask
        $(".number_mask").inputmask({
            mask: "13",
            repeat: 10,
            greedy: false
        }); // ~ mask "9" or mask "99" or ... mask "9999999999"
        
        // decimal format
        $(".numberic_mask").inputmask('integer', {
            rightAlignNumerics: false,
            digits : 0,
            min: 0,
        }); 
        
        // integer format
        $(".integer_mask").inputmask('integer', {
            groupSeparator : ',',
            autoGroup : true,
            digits : 0,
            min: 0,
            digitsOptional : false,
            rightAlignNumerics: false
        }); 
        
        // currency format
        $(".currency_mask").inputmask('999,999,999,999 vnđ', {
            numericInput: true
        }); //123456  =>   ___.__1.234,56

        //email address
        $(".email_mask").inputmask({
            mask: "*{1,20}[.*{1,20}][.*{1,20}][.*{1,20}]@*{1,20}[.*{2,6}][.*{1,2}]",
            greedy: false,
            onBeforePaste: function (pastedValue, opts) {
                pastedValue = pastedValue.toLowerCase();
                return pastedValue.replace("mailto:", "");
            },
            definitions: {
                '*': {
                    validator: "[0-9A-Za-z!#$%&'*+/=?^_`{|}~\-]",
                    cardinality: 1,
                    casing: "lower"
                }
            }
        });        
    }

    return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();
//== Class definition

var BootstrapDatepicker = function () {
    
    //== Private functions
    var demos = function () {

        // minimum setup
        $('.datepicker_min').datepicker({
        	format: 'dd/mm/yyyy',
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true,
            disableTouchKeyboard: true,
            enableOnReadonly: true,
            orientation: "bottom left",
            templates: {
                leftArrow: '<i class="la la-angle-left"></i>',
                rightArrow: '<i class="la la-angle-right"></i>'
            }
        });
        // minimum setup
        $('.datepicker_mindate').datepicker({
        	format: 'dd/mm/yyyy',
        	startDate: "-toDay",
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true,
            disableTouchKeyboard: true,
            enableOnReadonly: true,
            orientation: "bottom left",
            templates: {
                leftArrow: '<i class="la la-angle-left"></i>',
                rightArrow: '<i class="la la-angle-right"></i>'
            }
        });
        // minimum setup
        $('.datepicker_maxdate').datepicker({
        	format: 'dd/mm/yyyy',
        	endDate: new Date(),
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true,
            disableTouchKeyboard: true,
            enableOnReadonly: true,
            orientation: "bottom left",
            templates: {
                leftArrow: '<i class="la la-angle-left"></i>',
                rightArrow: '<i class="la la-angle-right"></i>'
            }
        });
        // enable clear button for modal demo
        $('.datepicker_modal').datepicker({
            format: 'dd/mm/yyyy',
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true,
            disableTouchKeyboard: true,
            enableOnReadonly: true,
            templates: {
                leftArrow: '<i class="la la-angle-left"></i>',
                rightArrow: '<i class="la la-angle-right"></i>'
            }
        });
        // enable clear button for modal demo
        $('.datepicker_min_modal').datepicker({
            format: 'dd/mm/yyyy',
            startDate: "-toDay",
            autoclose: true,
            todayBtn: "linked",
            disableTouchKeyboard: true,
            enableOnReadonly: true,
            todayHighlight: true,
            templates: {
                leftArrow: '<i class="la la-angle-left"></i>',
                rightArrow: '<i class="la la-angle-right"></i>'
            }
        });
    }

    return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();
//== Class definition

var BootstrapDaterangepicker = function () {
    
    //== Private functions
    var demos = function () {
        
        // predefined ranges
        var start = moment().subtract(29, 'days');
        var end = moment();
        
        $('.daterangepicker_multi').daterangepicker({
            buttonClasses: 'm-btn btn',
            applyClass: 'btn-primary',
            cancelClass: 'btn-secondary',
            startDate: start,
            endDate: end,
            locale: {
                format: 'DD/MM/YYYY HH:mm a'
            },
        }, function(start, end, label) {
        	$('.daterangepicker_multi .form-control').val( start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
            $('#to').val(start.format('DD/MM/YYYY'));
            $('#from').val(end.format('DD/MM/YYYY'));
        });
        $('.daterangepicker_search').daterangepicker({
            buttonClasses: 'm-btn btn',
            applyClass: 'btn-primary',
            cancelClass: 'btn-secondary',
            autoApply: true,
            startDate: start,
            endDate: end,
            ranges: {
               'Hôm nay': [moment(), moment()],
               'Hôm qua': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
               '7 ngày trước': [moment().subtract(6, 'days'), moment()],
               '30 ngày trước': [moment().subtract(29, 'days'), moment()],
               'Tháng này': [moment().startOf('month'), moment().endOf('month')],
               'Tháng trước': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        }, function(start, end, label) {
            $('.daterangepicker_search .form-control').val( start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
            $('#to').val(start.format('DD/MM/YYYY'));
            $('#from').val(end.format('DD/MM/YYYY'));
        });
        $('.daterangepicker_search2').daterangepicker({
            buttonClasses: 'm-btn btn',
            applyClass: 'btn-primary',
            cancelClass: 'btn-secondary',
            autoApply: true,
            startDate: start,
            endDate: end,
            ranges: {
               'Hôm nay': [moment(), moment()],
               'Hôm qua': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
               '7 ngày trước': [moment().subtract(6, 'days'), moment()],
               '30 ngày trước': [moment().subtract(29, 'days'), moment()],
               'Tháng này': [moment().startOf('month'), moment().endOf('month')],
               'Tháng trước': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        }, function(start, end, label) {
            $('.daterangepicker_search2 .form-control').val( start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
            $('#to2').val(start.format('DD/MM/YYYY'));
            $('#from2').val(end.format('DD/MM/YYYY'));
        });
        $('.daterangepicker_single').daterangepicker({
        	buttonClasses: 'm-btn btn',
            applyClass: 'btn-primary',
            cancelClass: 'btn-secondary',
            singleDatePicker: true,
            startDate: moment(),
            autoApply: true,
            showCustomRangeLabel: false,
            locale: {
                format: 'DD/MM/YYYY HH:mm a'
            },
        }, function(start, end, label) {
            $('.daterangepicker_single .form-control').val( start.format('DD/MM/YYYY HH:mm a') + ' - ' + end.format('DD/MM/YYYY HH:mm a'));
        });
        $('.daterangepicker_single_max').daterangepicker({
        	buttonClasses: 'm-btn btn',
            applyClass: 'btn-primary',
            cancelClass: 'btn-secondary',

            singleDatePicker: true,
            showDropdowns: true,
            maxDate: new Date(),
            autoApply: true,
            locale: {
                format: 'DD/MM/YYYY'
            },
        }, function(start, end, label) {
            $('.daterangepicker_single_max .form-control').val( start.format('DD/MM/YYYY'));
            $('#to').val(start.format('DD/MM/YYYY'));
        });
        $('.daterangepicker_single_min').daterangepicker({
        	buttonClasses: 'm-btn btn',
            applyClass: 'btn-primary',
            cancelClass: 'btn-secondary',
            singleDatePicker: true,
            minDate: moment(),
            startDate: moment(),
            autoApply: true,
            showCustomRangeLabel: false,
            locale: {
                format: 'DD/MM/YYYY HH:mm a'
            },
        }, function(start, end, label) {
            $('.daterangepicker_single_min .form-control').val( start.format('DD/MM/YYYY HH:mm a') + ' - ' + end.format('DD/MM/YYYY HH:mm a'));
        });
        $('.daterangepicker_single_time_min').daterangepicker({
        	buttonClasses: 'm-btn btn',
            applyClass: 'btn-primary',
            cancelClass: 'btn-secondary',
            singleDatePicker: true,
            timePicker: true,
            timePicker24Hour: true,
            minDate: moment(),
            startDate: moment(),
            autoApply: true,
            showCustomRangeLabel: false,
            locale: {
                format: 'DD/MM/YYYY HH:mm a'
            },
        }, function(start, end, label) {
            $('.daterangepicker_single_time_min .form-control').val( start.format('DD/MM/YYYY HH:mm a') + ' - ' + end.format('DD/MM/YYYY HH:mm a'));
        });
        
    }

    return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();
//== Class definition
var Select2 = function() {
    //== Private functions
    var demos = function() {
    	
    	// minimum setup
        $('.m_selectpicker').selectpicker();
        $('.m_select2').select2({
            placeholder: "Vui lòng chọn"
        });
        $('#giaocaModal').on('shown.bs.modal', function(){
            // multi select
            $('#slEmployee').select2({
                placeholder: "Vui lòng chọn"
            });
            $("#slEmployee").val(null).trigger("change");
        });
        $('#modalUpdateDinnertable').on('shown.bs.modal', function(){
            // multi select
            $('#cbxArea2').select2({
                placeholder: "Vui lòng chọn"
            });
            $("#cbxArea2").val(null).trigger("change");
        });
       
        $('#modalUpdateCustomer').on('shown.bs.modal', function(){
            // multi select
            $('#cbxCountryType').select2({
                placeholder: "Vui lòng chọn"
            });
            $("#cbxCountryType").val(null).trigger("change");
        });
        $('#changelpercent').on('shown.bs.modal',function(){
            // multi select
            $('#cbxProducts').select2({
                placeholder: "Vui lòng chọn"
            });
            $("#cbxProducts").val(null).trigger("change"); 
        });
    }

    //== Public functions
    return {
        init: function() {
            demos();
        }
    };
}();
jQuery(document).ready(function() {    
    Inputmask.init();
    BootstrapDatepicker.init();
    BootstrapDaterangepicker.init();
    BootstrapTimepicker.init();
    Select2.init();
    Autosize.init();
});