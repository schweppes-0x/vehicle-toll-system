import React from 'react';

interface PurchaseTollFormProps {
    purchaseData: { licensePlate: string; duration: 'WEEK' | 'MONTH' | 'YEAR' };
    setPurchaseData: React.Dispatch<React.SetStateAction<{ licensePlate: string; duration: 'WEEK' | 'MONTH' | 'YEAR' }>>;
    handleSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const PurchaseTollForm: React.FC<PurchaseTollFormProps> = ({ purchaseData, setPurchaseData, handleSubmit }) => (
    <div className="max-w-2xl mx-auto bg-white p-6 rounded shadow-md">
        <h2 className="text-2xl font-semibold mb-4">Purchase Toll</h2>
        <form onSubmit={handleSubmit}>
            <label className="block mb-4">
                <span className="text-gray-700">License Plate Number:</span>
                <input
                    type="text"
                    name="licensePlate"
                    value={purchaseData.licensePlate}
                    onChange={(e) => setPurchaseData({ ...purchaseData, licensePlate: e.target.value })}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                    required
                />
            </label>
            <label className="block mb-4">
                <span className="text-gray-700">Duration:</span>
                <select
                    name="duration"
                    value={purchaseData.duration}
                    onChange={(e) => setPurchaseData({ ...purchaseData, duration: e.target.value as 'WEEK' | 'MONTH' | 'YEAR' })}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                    required
                >
                    <option value="WEEK">Week</option>
                    <option value="MONTH">Month</option>
                    <option value="YEAR">Year</option>
                </select>
            </label>
            <button
                type="submit"
                className="px-4 py-2 bg-blue-500 text-white font-semibold rounded"
            >
                Purchase Toll
            </button>
        </form>
    </div>
);

export default PurchaseTollForm;
