
class Note {
    constructor(id, title, content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}

class NotesStorage {
    static KEY = 'notes_app_data';

    static save(notes) {
        localStorage.setItem(this.KEY, JSON.stringify(notes));
    }

    static load() {
        const data = localStorage.getItem(this.KEY);
        return data ? JSON.parse(data) : [];
    }
}

class NotesApp {
    constructor() {
        this.notes = NotesStorage.load();
        this.selectedId = null;

        this.listEl = document.getElementById('notesList');
        this.form = document.getElementById('noteForm');
        this.titleInput = document.getElementById('title');
        this.contentInput = document.getElementById('content');

        this.exportBtn = document.getElementById('exportBtn');
        this.importInput = document.getElementById('importInput');

        this.bindEvents();
        this.render();
    }

    bindEvents() {
        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveNote();
        });

        this.exportBtn.addEventListener('click', () => this.exportJSON());

        this.importInput.addEventListener('change', (e) => this.importJSON(e));
    }

    generateId() {
        return Date.now().toString();
    }

    saveNote() {
        const title = this.titleInput.value.trim();
        const content = this.contentInput.value.trim();

        if (!title) return;

        if (this.selectedId) {
            const note = this.notes.find(n => n.id === this.selectedId);
            note.title = title;
            note.content = content;
        } else {
            this.notes.push(new Note(this.generateId(), title, content));
        }

        NotesStorage.save(this.notes);
        this.resetForm();
        this.render();
    }

    selectNote(id) {
        const note = this.notes.find(n => n.id === id);
        this.selectedId = id;
        this.titleInput.value = note.title;
        this.contentInput.value = note.content;
    }

    resetForm() {
        this.selectedId = null;
        this.form.reset();
    }

    render() {
        this.listEl.innerHTML = '';
        this.notes.forEach(note => {
            const li = document.createElement('li');
            li.className = 'list-group-item list-group-item-action';
            li.textContent = note.title;
            li.onclick = () => this.selectNote(note.id);
            this.listEl.appendChild(li);
        });
    }

    exportJSON() {
        const blob = new Blob([JSON.stringify(this.notes, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'notes.json';
        a.click();
    }

    importJSON(event) {
        const file = event.target.files[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = (e) => {
            try {
                const imported = JSON.parse(e.target.result);
                if (Array.isArray(imported)) {
                    this.notes = imported;
                    NotesStorage.save(this.notes);
                    this.render();
                }
            } catch {
                alert('Błędny plik JSON');
            }
        };
        reader.readAsText(file);
    }
}

new NotesApp();