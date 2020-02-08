import React from 'react';
import { render } from '@testing-library/react';
import Root from './Root';

test('renders learn react link', () => {
  const { findByTitle } = render(<Root />);
  const rootTitle = findByTitle();
  expect(rootTitle).toBeInTheDocument();
});
