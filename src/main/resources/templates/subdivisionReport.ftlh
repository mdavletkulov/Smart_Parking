<#import "parts/common.ftlh" as c>
<#import "parts/dateReport.ftlh" as r>

<@c.page>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-5" role="alert">
            ${message}
        </div>
    </#if>
    <form action="/report/subdivision" method="post" class="mt-2">
        <div class="form-group col-3">
            <select class="form-control division" name="division" >
                <#list divisions as division>
                    <option>${division.getName()}</option>
                </#list>
            </select>
        </div>
        <div class="form-group col-3 subdivisions">
            <select class="form-control subdivision" name="division" hidden="hidden" >
            </select>
        </div>
        <@r.dateReport/>
    </form>

    <script>

        $(document).ready(function(){
            $("select.division").change(function(){
                var selectedDivision = $(this).children("option:selected").val();
                foo(selectedDivision);
            });
        });

        function foo(selectedDivision) {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "subdivision/get/"+selectedDivision,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    $("select.subdivision").empty();
                    $("select.subdivision").removeAttr("hidden");

                    for (var i=0; i < data.length; i++ )
                    {
                        populate('select.subdivision', data[i]);
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        }

        function populate(selector, name) {
            $(selector)
                .append('<option value="' + name +'">'+name+'</option>')
        }


    </script>



</@c.page>