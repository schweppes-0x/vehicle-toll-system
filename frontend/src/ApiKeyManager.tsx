import React, { useState } from 'react';

const ApiKeyManager: React.FC = () => {
    const [isFormVisible, setFormVisible] = useState(false);
    const [apiKey, setApiKey] = useState<string | null>(localStorage.getItem('apiKey'));

    const handleSaveApiKey = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const newApiKey = (event.currentTarget.elements.namedItem('apiKey') as HTMLInputElement).value;
        localStorage.setItem('apiKey', newApiKey);
        setApiKey(newApiKey);
        setFormVisible(false);
    };

    const handleRemoveApiKey = () => {
        localStorage.removeItem('apiKey');
        setApiKey(null);
    };

    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 p-4">
            <h1 className="text-4xl font-bold text-blue-500 mb-4">API Key Manager</h1>

            <button
                className="mb-4 px-4 py-2 bg-blue-500 text-white font-semibold rounded"
                onClick={() => setFormVisible(!isFormVisible)}
            >
                {isFormVisible ? 'Close Form' : 'Set/Edit API Key'}
            </button>

            {apiKey && (
                <div className="mb-4 text-green-600">
                    <p>Current API Key: <strong>{apiKey}</strong></p>
                    <button
                        className="px-4 py-2 bg-red-500 text-white font-semibold rounded mt-2"
                        onClick={handleRemoveApiKey}
                    >
                        Remove API Key
                    </button>
                </div>
            )}

            {isFormVisible && (
                <form
                    className="bg-white p-6 rounded shadow-md"
                    onSubmit={handleSaveApiKey}
                >
                    <label className="block mb-4">
                        <span className="text-gray-700">API Key:</span>
                        <input
                            type="text"
                            name="apiKey"
                            defaultValue={apiKey ?? ''}
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            required
                        />
                    </label>
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-500 text-white font-semibold rounded"
                    >
                        Save API Key
                    </button>
                </form>
            )}
        </div>
    );
};

export default ApiKeyManager;
