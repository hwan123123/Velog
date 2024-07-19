import {
    ClassicEditor,
    AccessibilityHelp,
    Autoformat,
    AutoImage,
    AutoLink,
    Autosave,
    BalloonToolbar,
    Bold,
    Code,
    CodeBlock,
    Essentials,
    Heading,
    ImageBlock,
    ImageCaption,
    ImageInline,
    ImageInsertViaUrl,
    ImageResize,
    ImageStyle,
    ImageTextAlternative,
    ImageToolbar,
    Italic,
    Link,
    LinkImage,
    List,
    ListProperties,
    Paragraph,
    SelectAll,
    Table,
    TableCaption,
    TableCellProperties,
    TableColumnResize,
    TableProperties,
    TableToolbar,
    TextTransformation,
    Undo
} from 'ckeditor5';

const editorConfig = {
    toolbar: {
        items: [
            'undo',
            'redo',
            '|',
            'selectAll',
            '|',
            'heading',
            '|',
            'bold',
            'italic',
            'code',
            '|',
            'link',
            'insertImageViaUrl',
            'insertTable',
            'codeBlock',
            '|',
            'bulletedList',
            'numberedList',
            '|',
            'accessibilityHelp'
        ],
        shouldNotGroupWhenFull: false
    },
    plugins: [
        AccessibilityHelp,
        Autoformat,
        AutoImage,
        AutoLink,
        Autosave,
        BalloonToolbar,
        Bold,
        Code,
        CodeBlock,
        Essentials,
        Heading,
        ImageBlock,
        ImageCaption,
        ImageInline,
        ImageInsertViaUrl,
        ImageResize,
        ImageStyle,
        ImageTextAlternative,
        ImageToolbar,
        Italic,
        Link,
        LinkImage,
        List,
        ListProperties,
        Paragraph,
        SelectAll,
        Table,
        TableCaption,
        TableCellProperties,
        TableColumnResize,
        TableProperties,
        TableToolbar,
        TextTransformation,
        Undo
    ],
    balloonToolbar: ['bold', 'italic', '|', 'link', '|', 'bulletedList', 'numberedList'],
    heading: {
        options: [
            { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
            { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
            { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
            { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
            { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
            { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
            { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
        ]
    },
    image: {
        toolbar: [
            'toggleImageCaption',
            'imageTextAlternative',
            '|',
            'imageStyle:inline',
            'imageStyle:wrapText',
            'imageStyle:breakText',
            '|',
            'resizeImage'
        ]
    },
    initialData: '',
    link: {
        addTargetToExternalLinks: true,
        defaultProtocol: 'https://',
        decorators: {
            toggleDownloadable: {
                mode: 'manual',
                label: 'Downloadable',
                attributes: {
                    download: 'file'
                }
            }
        }
    },
    list: {
        properties: {
            styles: true,
            startIndex: true,
            reversed: true
        }
    },
    placeholder: 'Type or paste your content here!',
    table: {
        contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties']
    }
};

ClassicEditor.create(document.querySelector('#editor'), editorConfig)
    .then(editorInstance => {
        window.editor = editorInstance;
        const editableElement = editorInstance.ui.view.editable.element;
        const toolbar = editorInstance.ui.view.toolbar.element;

        // Apply styles to the editor and toolbar
        applyEditorStyles(editableElement, toolbar);

        // Load initial content
        editorInstance.setData(document.querySelector('#content').value);

        // Add event listeners
        addEventListeners(editorInstance, toolbar);

        // Resize editor on window resize
        handleEditorResizing(toolbar);
    })
    .catch(error => {
        console.error(error);
    });

function applyEditorStyles(editableElement, toolbar) {
    editableElement.style.backgroundColor = 'black';
    editableElement.style.color = 'white';

    editableElement.addEventListener('focus', () => {
        editableElement.style.backgroundColor = 'black';
        editableElement.style.color = 'white';
    });
    editableElement.addEventListener('blur', () => {
        editableElement.style.backgroundColor = 'black';
        editableElement.style.color = 'white';
    });

    toolbar.style.backgroundColor = 'black';
    toolbar.querySelectorAll('.ck-button').forEach(button => {
        button.style.color = 'white';
    });
}

function addEventListeners(editorInstance, toolbar) {
    document.querySelector('.btn-submit').addEventListener('click', () => {
        const content = editorInstance.getData();
        const ment = document.querySelector('#previewComment').value;
        const title = document.querySelector('#postTitle').value;
        document.querySelector('#content').value = content;
        document.querySelector('#ment').value = ment;
        document.querySelector('#title').value = title;
    });
}

function handleEditorResizing(toolbar) {
    const resizeEditor = () => {
        const editorContainer = document.querySelector('.editor-container__editor');
        const toolbarHeight = toolbar.offsetHeight;
        const windowHeight = window.innerHeight;
        editorContainer.style.height = `${windowHeight - toolbarHeight - 60}px`; // Adjust for bottom bar height
    };

    // Initial resize
    resizeEditor();

    // Resize on window resize
    window.addEventListener('resize', resizeEditor);
}
