# LICENSING.md

## Annoying Villagers – Licensing Policy (Repository Rules)

This repository is **multi-licensed**.

### Default rule (no headers needed)
Unless a file is explicitly marked as third-party / upstream-licensed, it is considered **Original Material** and is licensed under:

- **Annoying Villagers Read-Only License (AV-RO-1.0)** → see `LICENSE`

This is intentional so that original files do not need per-file headers.

### Exceptions (must be marked)
Any file that contains, is copied from, or is adapted/derived from third-party sources **must be marked** as third-party licensed using one of:

1. A file header (recommended) like:
    - `SPDX-License-Identifier: MIT`
    - `SPDX-License-Identifier: GPL-3.0-only`
    - `SPDX-License-Identifier: LGPL-2.1-only`

2. Or listing the file/path in `THIRD_PARTY_NOTICES.md` under the correct license section.

If there is any conflict between AV-RO and an upstream license, **the upstream license controls for those portions**.

### Important note about GPL/LGPL/MIT
- You **cannot** take away permissions granted by upstream licenses (e.g., GPL/MIT allow redistribution/modification).
- This repo’s AV-RO restrictions apply only to **Original Material**.

### Summary
- **No header** = **AV-RO** (Original Material)
- **Header or listed in THIRD_PARTY_NOTICES.md** = upstream license applies
