<#macro dateReport>
    <div class="form-group col-4">
        <label for="startTime">Начальная дата</label>
        <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
            <input type="text" id="startTime" name="startTime" class="form-control ${(startTimeError??)?string('is-invalid', '')} datetimepicker-input"
                   data-target="#datetimepicker1"/>
            <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
            </div>
            <#if startTimeError??>
                <div class="invalid-feedback">
                    ${startTimeError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group col-4">
        <label for="endTime">Конечная дата</label>
        <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
            <input name="endTime" id="endTime" type="text" class="form-control ${(endTimeError??)?string('is-invalid', '')} datetimepicker-input"
                   data-target="#datetimepicker2"/>
            <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
            </div>
            <#if endTimeError??>
                <div class="invalid-feedback">
                    ${endTimeError}
                </div>
            </#if>
        </div>
    </div>


    <script type="text/javascript">


        $(function () {
            $('#datetimepicker1').datetimepicker({
                locale: 'ru'
            });
            $('#datetimepicker2').datetimepicker({
                locale: 'ru',
                useCurrent: false
            });
            $("#datetimepicker1").on("change.datetimepicker", function (e) {
                $('#datetimepicker2').datetimepicker('minDate', e.date);
            });
        });
    </script>
</#macro>

