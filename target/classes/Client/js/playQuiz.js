
function loadQuiz() {
    let QuestionsHTML = '<table>' +
        '<tr>' +
        '</tr>';
    fetch('/Questions/quizName', {method: 'get'}
    ).then(response => response.json()
    ).then(Questions => {

        for (let quizName of Questions) {
            let i = 0;
            let random = 0
            while (i <= 10) {
                random = Math.floor((Math.random() * Questions.size) + 1)
                i++;
            }
                QuestionsHTML += `<tr>` +
                    `<td>${quizName.quizName}</td>` +
                    `<td>${quizName.buttonSelection}</td>` +
                    `<td>${quizName.userName}</td>` +
                    `</td>` +
                    `</tr>`;
            }

            QuestionsHTML += '</table>';
            document.getElementById("listDiv").innerHTML = QuestionsHTML;

    })

}//end of function

function pageLoad(){
    loadQuiz();
}//end of function