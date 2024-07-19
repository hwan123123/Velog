document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.reply-button').forEach(button => {
        button.addEventListener('click', function() {
            const commentId = this.getAttribute('data-comment-id');
            toggleReplyForm(commentId);
        });
    });

    document.querySelectorAll('.cancel-reply-button').forEach(button => {
        button.addEventListener('click', function() {
            const commentId = this.getAttribute('data-comment-id');
            hideReplyForm(commentId);
        });
    });

    document.querySelectorAll('.delete-comment-button').forEach(button => {
        button.addEventListener('click', function() {
            const commentId = this.getAttribute('data-comment-id');
            if (confirm('댓글을 삭제하시겠습니까?')) {
                fetch(`/velog/comment/${commentId}/delete`, {
                    method: 'DELETE',
                    credentials: 'include'
                }).then(response => {
                    if (response.ok) {
                        location.reload();
                    } else {
                        alert('댓글 삭제에 실패했습니다.');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('댓글 삭제 중 오류가 발생했습니다.');
                });
            }
        });
    });

    document.querySelectorAll('.delete-reply-button').forEach(button => {
        button.addEventListener('click', function() {
            const replyId = this.getAttribute('data-reply-id');
            if (confirm('대댓글을 삭제하시겠습니까?')) {
                fetch(`/velog/reply/${replyId}/delete`, {
                    method: 'DELETE',
                    credentials: 'include'
                }).then(response => {
                    if (response.ok) {
                        location.reload();
                    } else {
                        alert('대댓글 삭제에 실패했습니다.');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('대댓글 삭제 중 오류가 발생했습니다.');
                });
            }
        });
    });
});

function toggleReplyForm(commentId) {
    const replyForm = document.getElementById('reply-form-' + commentId);
    if (replyForm) {
        replyForm.style.display = (replyForm.style.display === 'none' || replyForm.style.display === '') ? 'block' : 'none';
    } else {
        console.error('Reply form not found for commentId: ' + commentId);
    }
}

function hideReplyForm(commentId) {
    const replyForm = document.getElementById('reply-form-' + commentId);
    if (replyForm) {
        replyForm.style.display = 'none';
    } else {
        console.error('Reply form not found for commentId: ' + commentId);
    }
}
