function loadQuiz() {
    let Questions = '<table>' +
        '<tr>' +
        '<th>name</th>' +
        '</tr>'
    fetch('/Questions/quizName', {method: 'get'}
    ).then(response => response.json()
    ).then(quizName => {

        for (let quizName of Questions) {
            Questions += `<tr>` +
          `<td>${Questions.quizName}</td>` +
          `<td>${Questions.questionID}</td>` +
                `</td>` +
                `</tr>`;
        }
        Questions += '</table>'

        document.getElementById("listDiv").innerHTML = Questions;

    })

}//end of function