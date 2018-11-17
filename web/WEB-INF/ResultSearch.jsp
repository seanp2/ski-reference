<style>
    input[type="submit"] {
        color: black;
        background-color: #FFFFFF;
        border: 0;
        font-family: "Times New Roman", Times, serif;
        text-align: center;
        padding: 12px;
        text-decoration: none;
        font-size: 18px;
        line-height: 25px;
        border-radius: 4px;
    }
    input[type="submit"]:hover {
        background-color: #ddd;
        color: black;
    }
</style>
<form action="ResultSearch" method="get" style="font-size: larger">
    Result URL: <input style="font-size: larger;font-family:Times New Roman, Times, serif;"
                    type = "text" id="raceid" name="raceid">
    Discipline:
    <input type = "radio" name = "event" Value = "DH"> DH
    <input type = "radio"  name = "event" Value = "SG"> SG
    <input type = "radio"  name = "event" Value = "GS"> GS
    <input type = "radio" name = "event" Value = "SL"> SL
    <input type = submit value = Search>
</form>
