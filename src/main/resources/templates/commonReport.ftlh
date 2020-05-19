<#import "parts/reportNav.ftlh" as rN>
<#import "parts/dateReport.ftlh" as r>

<@rN.reportNav>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-5" role="alert">
            ${message}
        </div>
    </#if>
    <form method="post" action="/report/common">
    <div class="form-group col-4 typesJob">
        <label class="typesJob" for="typesJob" <#if !employee??> hidden="hidden"</#if>>Категории сотрудников (опционально)</label>
        <select class="form-control typesJob" name="typesJob" <#if !employee??> hidden="hidden"</#if>>
            <option selected="selected" value=""/>
            <#list typesJob as typeJob>
                <option>${typeJob}</option>
            </#list>
        </select>
    </div>
    <div class="form-group col-4 division">
        <label class="division" for="division" <#if employee??> hidden="hidden"</#if>>Институты (опционально)</label>
        <select class="form-control division" name="division" <#if employee??> hidden="hidden"</#if>>
            <option selected="selected" value=""/>
            <#list divisions as division>
                <option>${division.getName()}</option>
            </#list>
        </select>
    </div>

    <div class="form-group col-4 subdivisions">
        <label class="subdivision" for="subdivision"  hidden="hidden">Кафедры (опционально)</label>
        <select class="form-control subdivision" name="subdivision" hidden="hidden">
        </select>
    </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if employee??>
        <input type="hidden" name="employee" value="${employee}"/>
        </#if>
        <#if student??>
        <input type="hidden" name="student" value="${student}"/>
        </#if>
        <#if violation??>
            <input type="hidden" name="violation" value="${violation}"/>
        </#if>
    <@r.dateReport/>
    <button type="submit" class="btn btn-primary mt-3 send">Генерация отчета</button>
    </form>
    <script>


        var divisions = [<#list divisions as division>'${division.getName()}', </#list>];
        var _csrf = '${_csrf.token}';
        var divisionsSelect = $("select.division");
        var divisionsLabel = $("label.division");
        var typesJobSelect = $("select.typesJob");
        var typesJobLabel = $("label.typesJob");
        var subdivisionSelect = $("select.subdivision");
        var subdivisionLabel = $("label.subdivision");
        var employee = '${(employee??)?string('yes', 'no')}';


        $(document).ready(function () {
            console.log(divisions);
            subdivisionSelect.empty();
            if (employee === 'yes') {
                divisionsSelect.empty();
            }
            if (employee === 'yes') {
            typesJobSelect.change(function () {
                var selectedTypeJob = $(this).children("option:selected").val();
                division(selectedTypeJob);
            });
            }
            divisionsSelect.change(function () {
                if (!isEmpty($(this).children("option:selected"))) {
                    var selectedDivision = $(this).children("option:selected").val();
                    subdivision(selectedDivision);
                } else {

                        subdivisionSelect.empty();
                        subdivisionSelect.attr("hidden", "hidden");
                        subdivisionLabel.attr("hidden", "hidden");
                    }

            });
        });

        function subdivision(selectedDivision) {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/report/common/subdivision/" + selectedDivision,
                dataType: 'json',
                cache: false,    //This will force requested pages not to be cached by the browser
                timeout: 600000,
                success: function (data) {
                    subdivisionSelect.empty();
                    subdivisionSelect.removeAttr("hidden");
                    subdivisionLabel.removeAttr("hidden");
                    subdivisionSelect
                        .append('<option selected="selected" value=""/>');
                    for (var i = 0; i < data.length; i++) {
                        populate('select.subdivision', data[i]);
                    }
                }
            });
        }

        function populate(selector, name) {
            $(selector)
                .append('<option value="' + name + '">' + name + '</option>')
        }

        function division(selectedTypeJob) {
            if (selectedTypeJob === 'ППС') {
                divisionsSelect.empty();
                divisionsSelect.removeAttr("hidden");
                divisionsLabel.removeAttr("hidden");
                divisionsSelect
                    .append('<option selected="selected" value=""/>');
                for (var i = 0; i < divisions.length; i++) {
                    populate('select.division', divisions[i]);
                }
            } else {
                divisionsSelect.empty();
                divisionsSelect.attr("hidden", "hidden");
                divisionsLabel.attr("hidden", "hidden");
                subdivisionSelect.empty();
                subdivisionSelect.attr("hidden", "hidden");
                subdivisionLabel.attr("hidden", "hidden");
            }
        }

        function populate(selector, name) {
            $(selector)
                .append('<option value="' + name + '">' + name + '</option>')
        }

        function sendRequest() {
            var st = "${(startTimeError??)?string('yes', 'no')}";
            console.log(st);
            var reportRequest = createJson();
            console.log(JSON.stringify(reportRequest));
            var token = $("input[name='_csrf']").val();
            var header = "X-CSRF-TOKEN";
            $.ajaxSetup({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "common",
                dataType: 'json',
                data: JSON.stringify(reportRequest),
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: false,    //This will force requested pages not to be cached by the browser
                processData: false, //To avoid making query String instead of JSON
                timeout: 600000,
                success:function(response) {
                    window.location = '/report/common';
                },
                error: function (e) {
                    alert(e);
                }
            });
        }

        function isEmpty(el) {
            return !$.trim(el.html())
        }

        function createJson() {
            var requestJson = new Object();
            requestJson.typeJob = typesJobSelect.children("option:selected").val();
            requestJson.division = divisionsSelect.children("option:selected").val();
            requestJson.subdivision = subdivisionSelect.children("option:selected").val();
            requestJson.startTime = $('input[name ="startTime"]').val();
            requestJson.endTime = $('input[name ="endTime"]').val();
            // requestJson.student ='abc';
            // requestJson.employee ='abc';
            return requestJson;
        }


    </script>
</@rN.reportNav>