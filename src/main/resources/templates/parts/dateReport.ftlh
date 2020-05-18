<#macro dateReport>
    <div class="form-group col-3">
        <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
            <input type="text" id="startTime" name="startTime" class="form-control datetimepicker-input"
                   data-target="#datetimepicker1"/>
            <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
            </div>
        </div>
    </div>
    <div class="form-group col-3">
        <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
            <input name="endTime" id="endTime" type="text" class="form-control datetimepicker-input"
                   data-target="#datetimepicker2"/>
            <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
            </div>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary mt-3">Генерация отчета</button>

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

