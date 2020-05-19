<#import "common.ftlh" as c>
<#macro reportNav>
    <@c.page>
        <h5>Генерация отчетов</h5>
        <ul class="nav nav-pills mb-5">
            <li class="nav-item">
                <a class="nav-link report-a <#if simple??>active</#if>" href="/report/simple">Общий отчет в промежуток
                    времени</a>
            </li>
            <li class="nav-item report-a">
                <a class="nav-link report-a <#if person??>active</#if>" href="/report/person">Отчет по отдельным водителям</a>
            </li>
            <li class="nav-item dropdown report-a">
                <a class="nav-link dropdown-toggle report-a <#if criteria??>active</#if>" data-toggle="dropdown"
                   href="#"
                   role="button" aria-haspopup="true" aria-expanded="false">Отчет по различным критериям</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item report-a  <#if criteria_common??>active</#if>"
                       href="/report/common">Общий</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item report-a <#if criteria_employee??>active</#if>"
                       href="/report/common/employee">Сотрудники</a>
                    <a class="dropdown-item report-a <#if criteria_student??>active</#if>"
                       href="/report/common/student">Студенты</a>
                </div>
            </li>
            <li class="nav-item dropdown report-a">
                <a class="nav-link dropdown-toggle report-a <#if violation??>active</#if>" data-toggle="dropdown"
                   href="#"
                   role="button" aria-haspopup="true" aria-expanded="false">Отчет о нарушениях</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item report-a  <#if violation_common??>active</#if>"
                       href="/report/common/violation">Общий</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item report-a <#if violation_employee??>active</#if>"
                       href="/report/common/employee/violation">Сотрудники</a>
                    <a class="dropdown-item report-a <#if violation_student??>active</#if>"
                       href="/report/common/student/violation">Студенты</a>
                </div>
            </li>
            <li class="nav-item report-a">
                <a class="nav-link report-a <#if auto??>active</#if>" href="#">Статистика об автомобилях</a>
            </li>
        </ul>
        <script>
            var common = "${(simple??)?string('yes', 'no')}";
            var drivers = "${(person??)?string('yes', 'no')}";
            var criteria_common = "${(criteria_common??)?string('yes', 'no')}";
            var employee = "${(criteria_employee??)?string('yes', 'no')}";
            var students = "${(criteria_student??)?string('yes', 'no')}";
            var violation = "${(violation??)?string('yes', 'no')}";
            var violation_common = "${(violation_common??)?string('yes', 'no')}";
            var violation_employee = "${(violation_employee??)?string('yes', 'no')}";
            var violation_student = "${(violation_student??)?string('yes', 'no')}";
            var auto = "${(auto??)?string('yes', 'no')}";
            var criteria = "${(criteria??)?string('yes', 'no')}";
            var active = (common === 'yes' || drivers === 'yes' || criteria_common === 'yes' ||
                employee === 'yes' || students === 'yes' || violation === 'yes'
                || auto === 'yes' || criteria === 'yes' || violation_common === 'yes'
                || violation_employee === 'yes' || violation_student === 'yes');

            $('a.report-a').hover(
                function () {
                    if (!active)
                        $(this).addClass('active')
                },
                function () {
                    if (!active)
                        $(this).removeClass('active')
                }
            )

        </script>
        <#nested>

    </@c.page>
</#macro>