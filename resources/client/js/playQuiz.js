

function loadQuiz() {
    let QuestionsHTML = '<table>' +
        '<tr>' +
        '</tr>'
    fetch('/Questions/quizName', {method: 'get'}
    ).then(response => response.json()
    ).then(Questions => {

        for (let quizName of Questions) {
            QuestionsHTML += `<tr>` +
          `<td>${quizName.quizName}</td>` +
                `<td>${quizName.buttonSelection}</td>` +
                `</td>` +
                `</tr>`;
        }
        QuestionsHTML += '</table>';

        document.getElementById("listDiv").innerHTML = QuestionsHTML;

    })

}//end of function

function pageLoad(){
    loadQuiz();
}