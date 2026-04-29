# Tech Egg Mart v5 — Product Requirements Document

## 1. Overview

Tech Egg Mart v5 is an electronics retail web application inspired by Newegg.com's design, UX, and product catalog. Built with Spring Boot + Thymeleaf, it features a dark navy header, orange accent color scheme, category sidebar navigation, deal sections, and a full product browsing experience.

## 2. Design System

### Colors
| Token | Hex | Usage |
|-------|-----|-------|
| `--primary` | `#002d6a` | Header background, primary nav |
| `--primary-light` | `#1a4d8f` | Hover states on nav |
| `--accent` | `#f5a623` | Sale badges, deal highlights, Shell Shocker |
| `--accent-hover` | `#e6951a` | Button hover |
| `--cta` | `#e85d04` | Add to Cart buttons, primary CTAs |
| `--cta-hover` | `#d14e00` | CTA hover |
| `--bg` | `#ffffff` | Page background |
| `--bg-light` | `#f1f1f1` | Section backgrounds, sidebar |
| `--text` | `#212121` | Body text |
| `--text-muted` | `#666666` | Secondary text |
| `--border` | `#e0e0e0` | Borders, dividers |
| `--green` | `#2e8b3a` | In stock, free shipping badges |
| `--red` | `#cc0000` | Sale prices, limited stock |

### Typography
- **Font:** Open Sans, sans-serif
- **Body:** 14px / 400
- **Headings:** 16-28px / 600-700
- **Price large:** 24-32px / 700
- **Price cents:** 14px superscript
- **Badges:** 11px / 700 uppercase

### Layout
- Max width: 1280px centered
- Product grid: 4 columns desktop, 2 tablet, 1 mobile
- Sidebar: 220px fixed left
- Header: ~100px (top bar + search bar + nav bar)

## 3. Pages

### 3.1 Global Header
- **Top bar:** Dark navy `#002d6a`, full width
  - Logo (left): "TECH EGG" wordmark with egg icon
  - Search bar (center): Wide input with orange search button
  - Account (right): "Welcome, Sign In / Register"
  - Cart icon with badge count
- **Sub-nav bar:** Slightly lighter navy
  - Links: Shell Shocker, PC Builder, Best Sellers, Clearance, Today's Deals, Gaming
  - Right side: "NEWEGG BUSINESS" | "HELP CENTER"
- **Category sidebar** (homepage only):
  - Left panel listing: Components & Storage, Computer Systems, Peripherals, Electronics, Gaming & VR, Networking, Smart Home, Office Solutions, Software

### 3.2 Homepage
- **Hero carousel** (70% width, right of sidebar):
  - Rotating deal banners with product images, prices, CTA
- **Shell Shocker section:**
  - Featured daily deal with countdown timer placeholder
  - Large product card with image, name, price, savings percentage
- **Today's Best Deals grid:**
  - 4-column product card grid
  - Each card: image, title (2 lines), star rating, price (current + strikethrough), "Add to Cart"
  - "Free Shipping" badge on qualifying items
- **Category tiles row:**
  - Horizontal scroll of category image tiles
- **Footer:** Dark bg, 4-column link layout (Customer Service, My Account, Company, Tools)

### 3.3 Category/Search Results Page
- **Breadcrumbs:** Home > Category
- **Left sidebar filters:**
  - Subcategory checkboxes with counts
  - Brand checkboxes
  - Price range slider/inputs
  - "In Stock" toggle
  - Star rating filter
- **Toolbar:** Result count, Sort dropdown (Best Match, Price Low-High, Price High-Low, Best Rating, Most Reviews), Grid/List view toggle
- **Product grid:** 3-4 column cards
  - Product image (centered, white bg)
  - Product title (2-3 lines, linked)
  - Star rating (egg-shaped or standard stars) + review count
  - Price block: current price bold + original strikethrough + "SAVE X%"
  - "Free Shipping" green badge
  - "Add to Cart" orange button
- **Pagination:** Page numbers at bottom

### 3.4 Product Detail Page
- **Breadcrumbs:** Full path
- **3-column layout:**
  - Left (40%): Product image gallery with thumbnails
  - Center (30%): Title, brand, rating, key specs bullet list
  - Right (30%): Price box with current/original/savings, stock status, "ADD TO CART" button, shipping estimate, "Sold and shipped by Tech Egg Mart v5"
- **Tabbed section below:**
  - Overview: product description
  - Specifications: grouped table (General, Technical, Physical)
  - Reviews: star breakdown, individual reviews with rating/date/text
- **"Customers Also Viewed"** carousel at bottom

### 3.5 Shopping Cart
- Line items: image, name, unit price, quantity selector, line total, remove link
- Order summary sidebar: subtotal, estimated shipping, estimated tax, total
- "SECURE CHECKOUT" orange CTA button
- "Continue Shopping" link
- Promo code input

### 3.6 Search
- Search bar in header submits to /search?q=keyword
- Results page uses same layout as category page
- Autocomplete suggestions (stretch goal)

## 4. Data Model

### Product
- id (Long), sku (String), name (String), brand (String)
- shortDescription (String 200), longDescription (TEXT)
- price (BigDecimal), originalPrice (BigDecimal nullable)
- imageUrl (String), thumbnailUrls (JSON array)
- categoryId (FK), stock (Integer)
- freeShipping (boolean), featured (boolean)
- rating (Double 0-5), reviewCount (Integer)
- createdAt (Timestamp)

### Category
- id (Long), name (String), slug (String)
- parentId (Long FK nullable), iconClass (String)
- displayOrder (Integer)

### ProductSpecification
- id (Long), productId (FK), specGroup (String), specName (String), specValue (String)

### Review
- id (Long), productId (FK), userName (String)
- rating (Integer 1-5), title (String), body (TEXT)
- verified (boolean), helpfulCount (Integer), createdAt (Timestamp)

### CartItem
- id (Long), sessionId (String), productId (FK)
- quantity (Integer), addedAt (Timestamp)

## 5. Seed Data

### Categories (10+)
Components & Storage, Computer Systems, Computer Peripherals, Gaming & VR, Networking, Electronics, Smart Home, Office Solutions, Software, Automotive

### Products (40+ across categories)
- **CPUs (6):** AMD Ryzen 9 9950X3D, Ryzen 7 9800X3D, Ryzen 5 9600X, Intel Core Ultra 9 285K, i7 265K, i5 245K
- **GPUs (5):** RTX 5090, RTX 5080, RTX 5070 Ti, RX 9070 XT, RX 9070
- **Motherboards (4):** MSI MAG X870, ASUS ROG Strix B850, Gigabyte B650, ASRock X870E
- **RAM (4):** Corsair Vengeance 32GB DDR5, G.Skill Trident Z5 32GB, Kingston Fury 64GB, Crucial 16GB
- **Storage (4):** Samsung 990 Pro 2TB, WD Black SN850X 2TB, Crucial T700 2TB, Seagate Barracuda 4TB HDD
- **Cases (3):** NZXT H7 Flow, Corsair 4000D, Fractal North
- **PSUs (3):** Corsair RM850x, EVGA 750 G7, Seasonic Focus 1000W
- **Monitors (3):** ASUS ROG 27" OLED, LG UltraGear 27" QHD, Samsung Odyssey G9
- **Peripherals (4):** Logitech G Pro X, Razer BlackWidow, SteelSeries Arctis, HyperX Cloud III
- **Networking (3):** ASUS RT-AX86U, TP-Link Deco, Netgear Nighthawk

Each product should have: realistic price, some with originalPrice for sale display, 3-8 specs, imageUrl using placehold.co, freeShipping flag, rating 3.5-5.0, reviewCount 10-500.

## 6. Architecture

```
src/main/java/com/techegg/
  TechEggApplication.java
  domain/
    Product.java, Category.java, ProductSpecification.java
    Review.java, CartItem.java
  repository/
    ProductRepository.java, CategoryRepository.java
    ReviewRepository.java, CartItemRepository.java
    ProductSpecificationRepository.java
  service/
    ProductService.java — search, filter, paginate
    CartService.java — session-based cart ops
  controller/
    HomeController.java — homepage
    ProductController.java — category listing + detail
    CartController.java — cart operations
    SearchController.java — search results
  config/
    DataSeeder.java — seed data on startup

src/main/resources/
  templates/
    layout.html — base layout with header/footer fragments
    index.html — homepage
    category.html — category listing
    product.html — product detail
    cart.html — shopping cart
    search.html — search results
  static/
    css/style.css — Newegg-inspired styles
  application.yml
```

## 7. URL Structure
| URL | Page |
|-----|------|
| `/` | Homepage |
| `/category/{slug}` | Category listing |
| `/product/{id}` | Product detail |
| `/search?q=keyword` | Search results |
| `/cart` | Shopping cart |
| `/cart/add` | Add to cart (POST) |
| `/cart/update` | Update quantity (POST) |
| `/cart/remove/{id}` | Remove item (POST) |

## 8. Responsive Breakpoints
| Size | Width | Columns |
|------|-------|---------|
| Desktop | 1280px+ | 4-col grid |
| Tablet | 768-1279px | 2-col grid, collapsed sidebar |
| Mobile | <768px | 1-col, hamburger menu |

## 9. Key Newegg Design Elements to Replicate
- Dark navy header with orange search button
- Category sidebar on homepage (not inner pages)
- "Shell Shocker" deal section with countdown aesthetic
- Orange "Add to Cart" buttons throughout
- Product cards with white background, subtle border, hover shadow
- Star ratings displayed as filled/empty stars
- "FREE SHIPPING" green badges
- Strikethrough original prices with savings percentage
- Price displayed as $XXX.YY with cents in superscript
- Footer with dark background, organized link columns
