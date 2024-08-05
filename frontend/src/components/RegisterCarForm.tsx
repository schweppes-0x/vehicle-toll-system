import React from 'react';

interface RegisterCarFormProps {
    carData: { licensePlateNumber: string; manufactureDate: string; duration: 'WEEK' | 'MONTH' | 'YEAR' };
    setCarData: React.Dispatch<React.SetStateAction<{ licensePlateNumber: string; manufactureDate: string; duration: 'WEEK' | 'MONTH' | 'YEAR' }>>;
    handleSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const RegisterCarForm: React.FC<RegisterCarFormProps> = ({ carData, setCarData, handleSubmit }) => (
    <div className="max-w-2xl mx-auto bg-white p-6 rounded shadow-md mb-8">
        <h2 className="text-2xl font-semibold mb-4">Register a Car and Pay Toll</h2>
        <form onSubmit={handleSubmit}>
            <label className="block mb-4">
                <span className="text-gray-700">License Plate Number:</span>
                <input
                    type="text"
                    name="licensePlateNumber"
                    value={carData.licensePlateNumber}
                    onChange={(e) => setCarData({ ...carData, licensePlateNumber: e.target.value })}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                    required
                />
            </label>
            <label className="block mb-4">
                <span className="text-gray-700">Manufacture Date:</span>
                <input
                    type="date"
                    name="manufactureDate"
                    value={carData.manufactureDate}
                    onChange={(e) => setCarData({ ...carData, manufactureDate: e.target.value })}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                    required
                />
            </label>
            <label className="block mb-4">
                <span className="text-gray-700">Duration:</span>
                <select
                    name="duration"
                    value={carData.duration}
                    onChange={(e) => setCarData({ ...carData, duration: e.target.value as 'WEEK' | 'MONTH' | 'YEAR' })}
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
                Register and Pay Toll
            </button>
        </form>
    </div>
);

export default RegisterCarForm;
