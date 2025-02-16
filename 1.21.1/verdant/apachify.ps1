# Define the Apache 2.0 license header
$licenseHeader = @"
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
"@

# Define the target directory (prompt user if not provided)
$targetDir = Read-Host "Enter the directory to process"

# Ensure the directory exists
if (!(Test-Path $targetDir -PathType Container)) {
    Write-Host "Error: Directory does not exist." -ForegroundColor Red
    exit
}

# Get all .java files recursively
$files = Get-ChildItem -Path $targetDir -Recurse -Filter "*.java"

foreach ($file in $files) {
    $content = Get-Content -Path $file.FullName -Raw

    # Check if the file already has the Apache 2.0 license
    if ($content -match "Licensed under the Apache License, Version 2.0") {
        Write-Host "Skipping (already licensed): $($file.FullName)" -ForegroundColor Yellow
        continue
    }

    # Add the license header at the beginning
    $newContent = "$licenseHeader`n$content"

    # Write back the modified content
    $newContent | Set-Content -Path $file.FullName -Encoding UTF8

    Write-Host "Updated: $($file.FullName)" -ForegroundColor Green
}

Write-Host "License header added to applicable files." -ForegroundColor Cyan