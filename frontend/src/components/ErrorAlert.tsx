import React from 'react';

interface ErrorAlertProps {
    error: string | null;
}

const ErrorAlert: React.FC<ErrorAlertProps> = ({ error }) => (
    error ? (
        <div className="mt-4 p-4 bg-red-100 border border-red-200 rounded text-red-600">
            {error}
        </div>
    ) : null
);

export default ErrorAlert;
