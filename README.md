# HTML Depth Analyzer

This Java project retrieves and analyzes the HTML content from a given URL, extracts all textual content, verifies HTML structural correctness, and outputs the **deepest text** element found in the HTML DOM.

---

## 🧠 Features

- 🔍 **HTML Content Fetching** from a provided URL.
- 🧱 **HTML Structural Validation** using a basic stack-based parser.
- 📝 **Text Extraction** from the HTML body, tracking its depth within the tag hierarchy.
- ⛏️ **Deepest Text Finder**: Selects the text found at the greatest depth (deepest nested tag).
- ⚠️ **Malformation Detection**: Identifies and rejects malformed HTML (e.g., mismatched tags, unclosed tags).

---

## 📁 Project Structure

```
.
├── HtmlAnalyzer.java     # Main class for fetching, parsing, and analyzing HTML.
├── InnerText.java        # Data model class to store text content and its depth info.
```

---

## 📦 Requirements

- Java 8 or higher
- Internet connection (for fetching the URL's HTML)

---

## 🛠️ How It Works

### 1. **Fetch HTML**

```java
BufferedReader input = fetchHtmlContentFromUrl(url);
```
Opens a connection to the specified URL and retrieves the HTML as a stream.

---

### 2. **Parse and Validate HTML**

```java
LinkedList<InnerText> innerTexts = extractAllTextFromHtml(input);
```

- Uses a `Stack<String>` to validate opening/closing tags.
- Tracks the current depth based on open tags.
- Captures non-tag text (content) and stores its depth using the `InnerText` model.
- Throws `Exception("malfunction HTML")` on structural errors.

---

### 3. **Determine Deepest Text**

```java
String deepestText = getDeepestText(innerTexts);
```

- Finds the text content nested the deepest in the DOM.
- If multiple texts share the same depth, returns the one that appears first in order.

---

## 🚀 Usage

### 🧪 Running the Program

```bash
javac HtmlAnalyzer.java InnerText.java
java HtmlAnalyzer https://example.com
```
### 🔄 Sample Input (https://example.com)
```
<html>
	<head>
		<title>Example Title</title>
	</head>
	<body>
		<div>
			<p>Welcome to Example!</p>
		</div>
	</body>
</html>
```
### 🔄 Sample Output

```
Welcome to Example!
```
---

## 📄 Class Descriptions

### `InnerText`

| Field   | Type   | Description                           |
|---------|--------|---------------------------------------|
| `depth` | `int`  | Depth of the text in the DOM          |
| `text`  | `String` | The actual textual content            |
| `id`    | `int`  | Order of appearance for disambiguation |

---

### `HtmlAnalyzer`

| Method | Description |
|--------|-------------|
| `fetchHtmlContentFromUrl(String)` | Fetches HTML content from a URL |
| `extractAllTextFromHtml(BufferedReader)` | Parses HTML, validates tags, and extracts text |
| `getDeepestText(LinkedList<InnerText>)` | Finds and returns the deepest text node |
| `main(String[])` | Entry point that orchestrates fetching, parsing, and output |

---

## ⚠️ Limitations

- Assumes each HTML tag appears on its own line (line-based parsing).
- Doesn't support HTML attributes or JavaScript-rendered content.
- No support for self-closing tags (like `<br />`) — will be treated as malformed.

---

## 📌 Possible Improvements

- Use an HTML parser library like **JSoup** for more robust parsing.
- Support HTML attributes and real-world formatting.
- Add support for CLI options (e.g., verbose output, JSON format).

---

## 👨‍💻 Author

Crafted with care for educational and utility purposes. Feel free to fork and improve!
