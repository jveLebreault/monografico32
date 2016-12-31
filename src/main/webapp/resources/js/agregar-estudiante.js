(function($, undefined){
    $(document).ready(function(){
        var $transferidoCheckbox = $("#transferido");
        var $camposTransferidos = $("#camposTransferidos");
        var $camposTransferidosInpt = $("#camposTransferidos input");

        if( $($transferidoCheckbox).is(":checked") ) {
            $($camposTransferidos).show();
            $($camposTransferidosInpt).attr("required", "required");
        }

        $($transferidoCheckbox).on("change", function(){
            var attr = $($camposTransferidosInpt).attr("required");
            if( typeof attr !== typeof undefined && attr !== false){
                $camposTransferidosInpt.removeAttr("required");
            }
            else{
                $($camposTransferidosInpt).attr("required", "required");
            }

            $($camposTransferidos).toggle("fast");
        });
    });
})($);
