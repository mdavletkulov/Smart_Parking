<#import "../parts/reportNav.ftlh" as rN>
<#import "../parts/dateReport.ftlh" as r>
<@rN.reportNav>
    <div class="form-group">
        <input type="text" class="form-control pull-right" id="search" placeholder="Поиск по таблице">
    </div>
    <table class="table table-striped text-center" id="persons">
        <thead>
        <tr>
            <th scope="col">Водитель</th>
            <th scope="col">Автомобиль</th>
            <th scope="col">Номер</th>
            <th scope="col">Статус</th>
            <th scope="col">Подразделение</th>
            <th scope="col">Должность</th>
            <th scope="col">Институт</th>
            <th scope="col">Группа</th>
            <th scope="col">Курс</th>
            <th scope="col">Пропуск</th>
            <th scope="col">Дата истечения пропуска</th>
            <th scope="col">Специальный статус</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list persons as person>
            <tr>
                <th scope="row">${person.getFullName()}</th>
                <#list automobiles as auto>
                    <#if auto.getPerson().getId() == person.getId()><td>${auto.getModel()} </td></#if>
                    <#if auto.getPerson().getId() == person.getId()><td>${auto.getNumber()} </td></#if>
                </#list>
                <td><#if person.isStudent()>Студент</#if> <#if person.isEmployee()>Сотрудник</#if></td>
                <td><#if person.getJobPosition()??>${person.getJobPosition().getTypeJobPosition()}<#else>&#x2716;</#if></td>
                <td><#if person.getJobPosition()??>${person.getJobPosition().getNamePosition()}<#else>&#x2716;</#if></td>
                <td><#if person.getSubdivision()??>${person.getSubdivision().getDivision().getName()}<#else>&#x2716;</#if></td>
                <td><#if person.getGroupName()??>${person.getGroupName()}<#else>&#x2716;</#if></td>
                <td><#if person.getCourse()??>${person.getCourse()}<#else>&#x2716;</#if></td>
                <td><#if person.getPassNum()??>${person.getPassNum()}<#else>&#x2716;</#if></td>
                <td><#if person.getPassEndDate()??>${person.getPassEndDate()}<#else>&#x2716;</#if></td>
                <td><#if person.isSpecialStatus()>&#9745; <#else>&#x2716;</#if></td>
                <td><a href="/report/person/${person.getId()}" class="btn btn-primary stretched-link mt-3">Отчет</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@rN.reportNav>

<script>

    $(document).ready(function () {

        $("#search").keyup(function () {
            _this = this;
            $.each($("#persons tbody tr"), function () {
                if ($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                    $(this).hide();
                } else {
                    $(this).show();
                }
            });
        });
    });
</script>