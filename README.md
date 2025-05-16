# E-Commerce App with Paystack Integration

![App Screenshot](![image](https://github.com/user-attachments/assets/587fca26-6594-4cf4-a31b-ab8dd1a51835)
)

A modern Android e-commerce application built with Jetpack Compose, Firebase, and Paystack payment integration. The app features product browsing, cart management, user authentication, and secure payments.

## Features

### 1. User Authentication
- **Firebase Authentication** with email/password
- Secure sign-up and login flows
- Session management
- Form validation for auth fields

### 2. Product Catalog
- Browse products from API endpoint (`https://api.escuelajs.co/api/v1/products`)
- Product categories with filtering
- Search functionality
- Pull-to-refresh for latest products
- Shimmer loading animations
- Product details view

### 3. Shopping Cart
- Add/remove products
- Quantity adjustment
- Swipe-to-delete gesture
- Real-time total calculation
- Empty state UI
- Cart item counter badge

### 4. Checkout & Payments
- **Paystack Integration** for secure payments
- Order summary dialog
- Payment status tracking (success/failed/cancelled)
- Order confirmation flow
- Transaction reference tracking

### 5. Technical Implementation
- **Modern Android Architecture**:
  - MVVM pattern
  - Repository pattern
  - Clean architecture separation
- **Jetpack Components**:
  - Compose UI
  - Navigation Compose
  - ViewModel
  - Room Database
- **Networking**:
  - Retrofit for API calls
  - Coil for image loading
- **Dependency Injection** with Hilt
- **State Management**:
  - Compose state hoisting
  - Flow/LiveData for observation

## Screens

| Authentication | Product List | Product Details |
|----------------|--------------|------------------|
| ![Auth](![image](https://github.com/user-attachments/assets/4ffd4353-396b-4b43-9479-098ed8ea1f8d)) | ![Products](![image](https://github.com/user-attachments/assets/1ad360a8-4259-42f4-bd55-742a6b60277d)) | ![Details](![image](https://github.com/user-attachments/assets/a2e97f58-c59f-4bb8-9d94-646cd2af6358)
) |

| Shopping Cart | Checkout |
|---------------|----------|
| ![Cart](![image](https://github.com/user-attachments/assets/8eb1c4e2-46a2-4fc9-b1a5-06894e36a879)) | ![Checkout](![image](https://github.com/user-attachments/assets/a43a8fe9-48f4-4450-9531-cd3a6be1f7df)) |

## Architecture

```plaintext
app/
├── data/
│   ├── local/         # Room database entities and DAOs
│   ├── remote/        # Retrofit API services and DTOs
│   └── repository/    # Repository implementations
│
├── domain/            # Business logic and models
│   ├── model/         # Domain models
│   └── repository/    # Repository interfaces
│
├── features/          # Feature modules
│   ├── auth/          # Authentication
│   ├── products/      # Product catalog
│   └── cart/          # Shopping cart & checkout
│
└── ui/
    ├── components/    # Reusable UI components
    ├── theme/         # App theming
    └── navigation/    # Navigation graph

│
└── ui/
├── components/ # Reusable UI components
├── theme/ # App theming
└── navigation/ # Navigation graph
```

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Firebase project setup
- Paystack developer account

### Installation
1. Clone the repository
2. Add your configuration files:
   - `google-services.json` for Firebase
   - Add Paystack keys in `local.properties`:
     ```
     paystack.secretKey=your_secret_key
     paystack.publicKey=your_public_key
     ```
3. Build and run the app

## Configuration

### Firebase Setup
1. Create Firebase project
2. Enable Email/Password authentication
3. Add Android app and download `google-services.json`

### Paystack Integration
1. Register at [Paystack](https://paystack.com)
2. Get test API keys
3. Configure webhook for payment verification

## Libraries Used

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency injection
- [Retrofit](https://square.github.io/retrofit/) - HTTP client
- [Coil](https://coil-kt.github.io/coil/) - Image loading
- [Paystack Android SDK](https://github.com/PaystackHQ/paystack-android) - Payment processing
- [Firebase](https://firebase.google.com) - Authentication and database

## Testing

The app includes:
- Unit tests for ViewModels
- Integration tests for repositories
- UI tests for critical flows

Run tests with:
```bash
./gradlew test


