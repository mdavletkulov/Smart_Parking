<#import "../../parts/common.ftlh" as c>

<@c.page>
    <h5>Водитель</h5>
    <form action="${person}" method="post" class="mt-2">
        <div class="form-group">
            <label for="firstName">Имя</label>
            <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')} col-sm-4"
                   id="firstName"
                   value="<#if person??>${person.firstName}</#if>"
                   placeholder="Введите имя водителя"
                   name="firstName">
            <#if firstNameError??>
                <div class="invalid-feedback">
                    ${firstNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="secondName">Фамилия</label>
            <input type="text" class="form-control ${(secondNameError??)?string('is-invalid', '')} col-sm-4"
                   id="secondName"
                   value="<#if person??>${person.secondName}</#if>"
                   placeholder="Введите фамилию водителя"
                   name="secondName">
            <#if secondNameError??>
                <div class="invalid-feedback">
                    ${secondNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="middleName">Отчество</label>
            <input type="text" class="form-control ${(middleNameError??)?string('is-invalid', '')} col-sm-4"
                   id="middleName"
                   value="<#if person??>${person.middleName}</#if>"
                   placeholder="Введите отчество водителя"
                   name="middleName">
            <#if middleNameError??>
                <div class="invalid-feedback">
                    ${middleNameError}
                </div>
            </#if>
        </div>
        <div class="btn-group btn-group-toggle form-group" data-toggle="buttons">
            <label class="btn btn-secondary">
                <input type="radio" name="student" id="student" value=""> Студент
                <input type="radio" name="employee" id="employee" value=""> Сотрудник
            </label>
        <#if studentError??>
            <div class="invalid-feedback mb-3" style="display: block">
                ${studentError}
            </div>
        </#if>
        <#if employeeError??>
            <div class="invalid-feedback mb-3" style="display: block">
                ${employeeError}
            </div>
        </#if>
        </div>
        <div class="form-group" hidden="hidden">
            <label for="courseError">Курс</label>
            <input type="text" class="form-control ${(courseError??)?string('is-invalid', '')} col-sm-4"
                   id="courseError"
                   value="<#if person??>${person.course}</#if>"
                   placeholder="Введите курс водителя"
                   name="courseError">
            <#if courseError??>
                <div class="invalid-feedback">
                    ${courseError}
                </div>
            </#if>
        </div>
        <div class="form-group" hidden="hidden">
            <label for="groupName">Группа</label>
            <input type="text" class="form-control ${(groupNameError??)?string('is-invalid', '')} col-sm-4"
                   id="groupName"
                   value="<#if person??>${person.groupName}</#if>"
                   placeholder="Введите группу водителя"
                   name="groupName">
            <#if groupNameError??>
                <div class="invalid-feedback">
                    ${groupNameError}
                </div>
            </#if>
        </div>
        <div class="form-group col-4 jobPosition">
            <label for="jobPosition">Должность</label>
            <select class="form-control division" name="jobPosition">
                <option selected="selected" value=""/>
                <#list jobPositions as jobPosition>
                    <option>${jobPosition.getName()}</option>
                </#list>
            </select>
            </label>
            <#if jobPositionError??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${jobPositionError}
                </div>
            </#if>
        </div>
        <div class="form-group col-4 division">
            <label class="division" for="division">Институт</label>
            <select class="form-control division" name="division">
                <option selected="selected" value=""/>
                <#list divisions as division>
                    <option>${division.getName()}</option>
                </#list>
            </select>
        </div>
        <div class="form-group col-4 subdivision">
            <label class="subdivision" for="subdivision" hidden="hidden">Кафедра</label>
            <select class="form-control subdivision" name="subdivision" hidden="hidden">
                <option selected="selected" value=""/>
            </select>
            <#if subdivisionError??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${subdivisionError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="passNum">Номер пропуска</label>
            <input type="text" class="form-control ${(passNumError??)?string('is-invalid', '')} col-sm-4"
                   id="passNum"
                   value="<#if person??>${person.passNum}</#if>"
                   placeholder="Введите номер пропуска водителя"
                   name="passNum">
            <#if passNumError??>
                <div class="invalid-feedback">
                    ${passNumError}
                </div>
            </#if>
        </div>

        <div class="form-group col-4">
            <label for="passEndDate">Срок истечения пропуска</label>
            <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
                <input type="text" id="passEndDate" name="passEndDate"
                       class="form-control ${(passEndDateError??)?string('is-invalid', '')} datetimepicker-input"
                       data-target="#datetimepicker2" placeholder="Введите дату истечения пропуска"
                       value="<#if person??>${person.passEndDate}</#if> "/>
                <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                </div>
                <#if passEndDateError??>
                    <div class="invalid-feedback">
                        ${passEndDateError}
                    </div>
                </#if>
            </div>
        </div>

        <div class="form-check">
            <input name="specialStatus" type="checkbox"
                   class="form-check-input ${(specialStatusError??)?string('is-invalid', '')}" id="specialStatus">
            <label class="form-check-label" for="exampleCheck1">Специальный статус</label>
            <#if specialStatusError??>
                <div class="invalid-feedback">
                    ${specialStatusError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>

    <script>

        var _csrf = '${_csrf.token}';
        var divisionsSelect = $("select.division");
        var divisionsLabel = $("label.division");
        var subdivisionSelect = $("select.subdivision");
        var subdivisionLabel = $("label.subdivision");

        $(document).ready(function () {
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

        function isEmpty(el) {
            return !$.trim(el.html())
        }


        $(function () {
            $('#datetimepicker2').datetimepicker({
                locale: 'ru'
            });
        });
    </script>
</@c.page>