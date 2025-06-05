# Deep Fitness UI

A modern fitness tracking web application built with **React**, **TypeScript**, and **Vite**.  
This project is part of the Deep Fitness microservices suite.

---

## Table of Contents

- [About](#about)
- [Requirements](#requirements)
- [Installation](#installation)
- [Available Scripts](#available-scripts)
- [Project Structure](#project-structure)
- [ESLint & Code Quality](#eslint--code-quality)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## About

**Deep Fitness UI** is a responsive frontend for tracking user activities, viewing AI-powered recommendations, and managing fitness data.  
It communicates with backend microservices via REST APIs.

---

## Requirements

- **Node.js**: `>=20.0.0` (LTS recommended)
- **npm**: `>=9.0.0`
- **TypeScript**: `>=5.0.0`
- **Vite**: `>=4.0.0` (installed as a dev dependency)
- **Jest**: for unit testing

> **Check your versions:**
> ```sh
> node -v
> npm -v
> npx tsc -v
> ```

---

## Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-org/deep-fitness-microservices.git
   cd deep-fitness-microservices/deepfitness-ui
   ```

2. **Install dependencies:**
   ```sh
   npm install
   ```

3. **Start the development server:**
   ```sh
   npm run dev
   ```

4. **Build for production:**
   ```sh
   npm run build
   ```

---

## Available Scripts

- `npm run dev` — Start the Vite development server
- `npm run build` — Build the app for production
- `npm run preview` — Preview the production build
- `npm test` — Run all Jest tests
- `npm run lint` — Run ESLint

---

## Project Structure

```
src/
  components/         # React components
  interceptors/       # Axios interceptors and API utilities
  store/              # Redux slices and store setup
  App.tsx             # Main app component
  main.tsx            # Entry point
public/
  index.html
```

---

## ESLint & Code Quality

This project uses **ESLint** with recommended TypeScript and React rules.

### Type-Aware Linting

For production, enable type-aware lint rules in your `eslint.config.js`:

```js
export default tseslint.config({
  extends: [
    // Remove ...tseslint.configs.recommended and replace with this
    ...tseslint.configs.recommendedTypeChecked,
    // Alternatively, use this for stricter rules
    ...tseslint.configs.strictTypeChecked,
    // Optionally, add this for stylistic rules
    ...tseslint.configs.stylisticTypeChecked,
  ],
  languageOptions: {
    // other options...
    parserOptions: {
      project: ['./tsconfig.node.json', './tsconfig.app.json'],
      tsconfigRootDir: import.meta.dirname,
    },
  },
})
```

### React-Specific Lint Rules

Install and configure [eslint-plugin-react-x](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-x) and [eslint-plugin-react-dom](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-dom):

```js
// eslint.config.js
import reactX from 'eslint-plugin-react-x'
import reactDom from 'eslint-plugin-react-dom'

export default tseslint.config({
  plugins: {
    // Add the react-x and react-dom plugins
    'react-x': reactX,
    'react-dom': reactDom,
  },
  rules: {
    // other rules...
    // Enable its recommended typescript rules
    ...reactX.configs['recommended-typescript'].rules,
    ...reactDom.configs.recommended.rules,
  },
})
```

---

## Testing

- **Unit tests** are written with [Jest](https://jestjs.io/) and [React Testing Library](https://testing-library.com/).
- To run all tests:
  ```sh
  npm test
  ```
- To run a specific test file:
  ```sh
  npx jest src/components/Home.test.tsx
  ```

---

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a pull request

---
