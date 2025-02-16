Get-ChildItem -Path . -Filter *.java -Recurse | ForEach-Object {
    $content = [System.IO.File]::ReadAllText($_.FullName)
    $utf8NoBom = New-Object System.Text.UTF8Encoding $False  # UTF-8 without BOM
    [System.IO.File]::WriteAllText($_.FullName, $content, $utf8NoBom)
}