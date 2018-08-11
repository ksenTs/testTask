function add() {

    var name =  $("#newName").val();
    var description = $("#newDescr").val();
    var category = $("#newCateg").val();

    if (name !== "" && description !== "" && category !== "")
    {
        $.ajax({
            url:"/addGood",
            type: "POST",
            data:{
                name: name,
                description: description,
                categoryName: category
            },
            dataType: "json"
        });

        alert("товар сохранён");
    }
    else
        alert("пожалуйста, заполните пустые поля");
}